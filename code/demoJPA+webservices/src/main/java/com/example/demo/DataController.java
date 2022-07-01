package com.example.demo;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.data.*;

import com.example.formdata.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class DataController {

    int typeUser = 0;

    @Autowired
    UserService userService;

    @Autowired
    TeamService teamService;

    @Autowired
    PlayerService playerService;

    @Autowired
    MatchService matchService;

    @Autowired
    InterruptService interruptService;

    @Autowired
    ResumeService resumeService;

    @Autowired
    GoalService goalService;

    @Autowired
    YellowService yellowService;

    @Autowired
    RedService redService;

    @Autowired
    EventService eventService;

    @Autowired
    RESTcontroller restController;

    @GetMapping("/")
    public String redirect() {
        return "redirect:/begin";
    }

    @GetMapping("/begin")
    public String begin() {
        return "begin";
    }

    @GetMapping("/completeDB")
    public String completeDB() throws Exception {

        this.restController.getTeams();
        this.restController.getPlayers();

        return "redirect:/createData";
    }

    @GetMapping("/createData")
    public String createData() { return "createData"; }

    @GetMapping("/login")
    public String greetingForm(Model model) {
        model.addAttribute("login", new Users());
        return "login";
    }

    @PostMapping("/login")
    public String greetingSubmit(@ModelAttribute Users user, Model model) {

        List<Users> lu = this.userService.getAllUsers();
        for (Users u : lu) {
            if (u.getEmail().equals(user.getEmail())) {
                if(u.getPassword().equals((user.getPassword()))) {
                    model.addAttribute("login", user);
                    System.out.println(u.isAdmin());
                    if(u.isAdmin()) {
                        typeUser = 2;
                        return "redirect:/admin";
                    }
                    else {
                        typeUser = 1;
                        return "redirect:/afterlogin";
                    }
                }
            }
        }
        return "createData";

    }

    @GetMapping("/anonymous")
    public String anonymous(Model model) {

        model.addAttribute("matches", this.matchService.findCurrentMatches());
        return "anonymous";
    }

    @GetMapping("/match_inside")
    public String match_inside(@RequestParam(name="id", required=true) int id, Model model) {
        Optional<Match> op = this.matchService.getMatchById(id);

        if (op.isPresent()) {

            if (op.get().isStart() && (!op.get().isEnd_())) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime actual = LocalDateTime.now();
                dtf.format(actual);
                Timestamp finalDateTimeLocal = Timestamp.valueOf(actual);

                long dif = finalDateTimeLocal.getTime() - op.get().getStart_time().getTime();

                model.addAttribute("match_time",dif / 1000 / 60);
            }

            model.addAttribute("match", op.get());

            int idA = op.get().getTeamA().getId();
            int idB = op.get().getTeamB().getId();

            String res = this.goalService.findResult(op.get().getId(), idA, idB);

            model.addAttribute("result", res);


            Optional<List<Event>> listEvents = this.eventService.getEventsByMatchId(id);

            if (listEvents.isPresent()) {
                model.addAttribute("events", listEvents.get());
            }
        }
        return "match_inside";
    }

    @GetMapping("/best_scorer")
    public String best_scorer(Model model) {

        Optional<List<Integer>> listN_goals = goalService.getBestScorer();

        if (listN_goals.get().size() != 0) {
            int nGoals = listN_goals.get().get(0);

            System.out.println("AQUII 1 " + listN_goals.get().get(0) + " ### " + listN_goals.get().get(0).getClass().getName());

            Optional<List<Integer>> listIdPlayers = goalService.getScorer(((long) nGoals));

            System.out.println("AQUII 2 " + listN_goals.get().get(0) + " ### " + listN_goals.get().get(0).getClass().getName());

            if (listIdPlayers.isPresent()) {
                List<Player> listPlayers = new ArrayList<>();

                Optional<Player> p = this.playerService.getPlayerById(listIdPlayers.get().get(0));
                if (p.isPresent()) {
                    listPlayers.add(p.get());

                    model.addAttribute("nGoals", nGoals);
                    model.addAttribute("Player", listPlayers.get(0));

                    return "best_scorer";
                }
            }

        }
        if (typeUser == 0)
            return "redirect:/anonymous";
        else if (typeUser == 1)
            return "redirect:/afterlogin";
        else
            return "redirect:/admin";
    }

    @PostMapping("/best_scorer")
    public String best_scorer(@ModelAttribute Users user, Model model) {
        if (typeUser == 0)
            return "redirect:/anonymous";
        else if (typeUser == 1)
            return "redirect:/afterlogin";
        else
            return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        if (typeUser == 2) {
            model.addAttribute("matches", this.matchService.findCurrentMatches());

            model.addAttribute("allmatches", this.matchService.findCurrentAndFutureMatches());

            return "admin";
        }
        else
            return "redirect:/createData";
    }

    @GetMapping("/afterlogin")
    public String afterlogin(Model model) {

        if (typeUser != 0) {
            model.addAttribute("matches", this.matchService.findCurrentMatches());

            model.addAttribute("allmatches", this.matchService.findCurrentAndFutureMatches());

            System.out.println(this.matchService.getAllMatches());

            return "afterlogin";
        }
        else
            return "redirect:/createData";

    }

    @GetMapping("/register")
    public String register(Model model) {
        if (typeUser == 2) {
            model.addAttribute("register", new Users());
            return "register";
        }
        else
            return "redirect:/createData";
    }

    @PostMapping("/register")
    public String registar(@ModelAttribute Users user, Model model) {

        if ((!user.getEmail().equals("")) && (!user.getPassword().equals("")) && (!user.getName().equals("")) && (!user.getTelephone().equals(""))) {
            List<Users> lu = this.userService.getAllUsers();
            for (Users u : lu) {
                if (u.getEmail().equals(user.getEmail())) {
                    return "redirect:/register";
                }
            }

            user.setAdmin(false);

            model.addAttribute("register", user);

            this.userService.addUser(user);

            return "redirect:/createData";
        }

        return "redirect:/register";

    }

    @GetMapping("/team")
    public String equipa(Model model) {
        if (typeUser == 2) {
            model.addAttribute("team", new Team());
            return "team";
        }
        else
            return "redirect:/createData";
    }

    @PostMapping("/team")
    public String teams(@ModelAttribute Team team, Model model) {

        if ((!team.getName().equals("")) && (!team.getImage().equals(""))) {
            team.setN_defeats(0);
            team.setN_wins(0);
            team.setN_draws(0);
            team.setN_matches(0);

            model.addAttribute("team", team);

            this.teamService.addTeam(team);

            return "redirect:/admin";
        }
        return "redirect:/team";
    }


    @GetMapping("/player")
    public String player(Model model) {
        if (typeUser == 2) {
            model.addAttribute("allteams", this.teamService.getAllTeams());

            model.addAttribute("player", new PlayerForm());
            return "player";
        }
        else
            return "redirect:/createData";
    }

    @PostMapping("/player")
    public String player(PlayerForm pf) throws ParseException {

        if ((pf.getT() != null) && (!pf.getName().equals("")) && (!pf.getPosition().equals("")) && (!pf.getBirth().equals(""))) {
            Player p = new Player();

            p.setName(pf.getName());
            p.setPosition(pf.getPosition());
            p.setT(pf.getT());

            Date birth = new SimpleDateFormat("yyyy-MM-dd").parse(pf.getBirth());
            p.setBirth(birth);

            p.setGoals(new ArrayList<>());
            p.setReds(new ArrayList<>());
            p.setYellows(new ArrayList<>());

            this.playerService.addPlayer(p);

            return "redirect:/admin";
        }

        return "redirect:/player";
    }


    @GetMapping("/match")
    public String match(Model model) {
        if (typeUser == 2) {
            model.addAttribute("allteams", this.teamService.getAllTeams());

            model.addAttribute("match", new MatchForm());
            return "match";
        }
        else
            return "redirect:/createData";
    }

    @PostMapping("/match")
    public String match(@ModelAttribute MatchForm match, Model model) {

        if ((!match.getDatetime().equals("")) && (!match.getLocation().equals("")) && (match.getTeamA() != null) && (match.getTeamB() != null)) {
            Match realMatch = new Match();

            Optional<Team> tA = teamService.getTeamByName(match.getTeamA());

            if (tA.isPresent()) {
                realMatch.setTeamA(tA.get());

                Optional<Team> tB = teamService.getTeamByName(match.getTeamB());

                if (tB.isPresent()) {
                    realMatch.setTeamB(tB.get());

                    String okDateTimeLocal = (match.getDatetime().replace("T", " ")).concat(":00");
                    Timestamp finalDateTimeLocal = Timestamp.valueOf(okDateTimeLocal);

                    realMatch.setDate(finalDateTimeLocal);

                    realMatch.setLocation(match.getLocation());

                    realMatch.setEnd_(false);
                    realMatch.setGoals(new ArrayList<>());
                    realMatch.setInterrupted(false);
                    realMatch.setInterruptions(new ArrayList<>());
                    realMatch.setReds(new ArrayList<>());
                    realMatch.setLast_event(okDateTimeLocal);
                    realMatch.setResumes(new ArrayList<>());
                    realMatch.setStart(false);
                    realMatch.setYellows(new ArrayList<>());

                    model.addAttribute("match", realMatch);

                    this.matchService.addMatch(realMatch);

                    return "redirect:/admin";
                }
            }
        }

        return "redirect:/match";

    }



    @GetMapping("/match_events")
    public String match_events(@RequestParam(name="id", required=true) int id, Model model) {
        //model.addAttribute("team", new Team());

        if (typeUser != 0) {
            Optional<Match> op = this.matchService.getMatchById(id);

            if (op.isPresent())
                model.addAttribute("match", op.get());

            return "match_events";
        }
        else
            return "redirect:/createData";
    }

    @PostMapping("/match_events")
    public String match_events(@ModelAttribute Match match, Model model) {

        model.addAttribute("match", match);

        //this.matchService.addMatch(match);

        return "redirect:/admin";
    }


    @GetMapping("/champ_table")
    public String champ_table(Model model) {
        return "champ_table";
    }

    @PostMapping("/champ_table")
    public String champ_table() {
        return "redirect:/anonymous";
    }

    @GetMapping("/table_matches")
    public String table_matches(Model model) {
        Optional<List<Team>> op = this.teamService.getTableMatches();

        if (op.isPresent())
            model.addAttribute("teams", op.get());

        return "table_matches";
    }

    @PostMapping("/table_matches")
    public String table_matches() {

        return "redirect:/anonymous";
    }

    @GetMapping("/table_wins")
    public String table_wins(Model model) {
        Optional<List<Team>> op = this.teamService.getTableWins();

        if (op.isPresent())
            model.addAttribute("teams", op.get());

        return "table_wins";
    }

    @PostMapping("/table_wins")
    public String table_wins() {

        return "redirect:/anonymous";
    }

    @GetMapping("/table_draws")
    public String table_draws(Model model) {
        Optional<List<Team>> op = this.teamService.getTableDraws();

        if (op.isPresent())
            model.addAttribute("teams", op.get());

        return "table_draws";
    }

    @PostMapping("/table_draws")
    public String table_draws() {

        return "redirect:/anonymous";
    }

    @GetMapping("/table_defeats")
    public String table_defeats(Model model) {
        Optional<List<Team>> op = this.teamService.getTableDefeats();

        if (op.isPresent())
            model.addAttribute("teams", op.get());

        return "table_defeats";
    }

    @PostMapping("/table_defeats")
    public String table_defeats() {

        return "redirect:/anonymous";
    }

    @GetMapping("/start_match")
    public String start_match(@RequestParam(name="id", required=true) int id, Model model) {

        if (typeUser != 0) {
            Optional<Match> op = this.matchService.getMatchById(id);

            StartMatchForm smf = new StartMatchForm();
            smf.setId(id);

            if (op.isPresent()) {
                model.addAttribute("matchf", smf);
                model.addAttribute("match", op.get());
            }
            return "start_match";
        }
        else
            return "redirect:/createData";
    }

    @PostMapping("/start_match")
    public String start_match(@RequestParam(name="id", required=true) int id, @ModelAttribute StartMatchForm matchf) {

        if (!(matchf.getTime().equals(""))) {
            String okDateTimeLocal = (matchf.getTime().replace("T", " ")).concat(":00");
            Timestamp finalDateTimeLocal = Timestamp.valueOf(okDateTimeLocal);

            this.matchService.changeStart(id, finalDateTimeLocal);

        }


        if (typeUser == 1)
            return "redirect:/afterlogin";
        return "redirect:/admin";
    }

    @GetMapping("/end_match")
    public String end_match(@RequestParam(name="id", required=true) int id, Model model) {

        if (typeUser != 0) {
            Optional<Match> op = this.matchService.getMatchById(id);

            StartMatchForm emf = new StartMatchForm();
            emf.setId(id);

            if (op.isPresent()) {
                model.addAttribute("matchf", emf);
                model.addAttribute("match", op.get());
            }

            return "end_match";
        }
        else
            return "redirect:/createData";

    }

    @PostMapping("/end_match")
    public String end_match(@RequestParam(name="id", required=true) int id, @ModelAttribute StartMatchForm matchf) {

        if (!matchf.getTime().equals("")) {
            Optional<Match> op = this.matchService.getMatchById(id);

            if (op.isPresent()) {
                int GoalsA = this.goalService.getNGoalsFromTeam(op.get().getId(), op.get().getTeamA().getId());
                int GoalsB = this.goalService.getNGoalsFromTeam(op.get().getId(), op.get().getTeamB().getId());

                String okDateTimeLocal = (matchf.getTime().replace("T", " ")).concat(":00");
                Timestamp finalDateTimeLocal = Timestamp.valueOf(okDateTimeLocal);

                this.matchService.changeEnd(op.get().getId(), GoalsA, GoalsB, finalDateTimeLocal);

                Team tA = op.get().getTeamA();
                Team tB = op.get().getTeamB();

                tA.setN_matches(tA.getN_matches()+1);
                tB.setN_matches(tB.getN_matches()+1);

                if (GoalsA > GoalsB) {
                    tA.setN_wins(tA.getN_wins()+1);
                    tB.setN_defeats(tB.getN_defeats()+1);
                }
                else if (GoalsB > GoalsA) {
                    tA.setN_defeats(tA.getN_defeats()+1);
                    tB.setN_wins(tB.getN_wins()+1);
                }
                else {
                    tA.setN_draws(tA.getN_draws()+1);
                    tB.setN_draws(tB.getN_draws()+1);
                }

                this.teamService.addTeam(tA);
                this.teamService.addTeam(tB);
            }
        }

        if (typeUser == 1)
            return "redirect:/afterlogin";

        return "redirect:/admin";
    }

    @GetMapping("/interrupt_match")
    public String interrupt_match(@RequestParam(name="id", required=true) int id, Model model) {

        if (typeUser != 0) {
            Optional<Match> op = this.matchService.getMatchById(id);

            if (op.isPresent())
                model.addAttribute("match", op.get());

            model.addAttribute("interrupt", new InterruptForm());

            return "interrupt_match";
        }
        else
            return "redirect:/createData";
    }

    @PostMapping("/interrupt_match")
    public String interrupt_match(@RequestParam(name="id", required=true) int id, @ModelAttribute InterruptForm interrupt) {

        if (!interrupt.getTime().equals("")) {
            Optional<Match> op = this.matchService.getMatchById(id);
            if (op.isPresent()) {
                Interrupt realInterrupt = new Interrupt();

                if ((op.get().isStart()) && !(op.get().isEnd_()) && !(op.get().isInterrupted())) {

                    String okDateTimeLocal = (interrupt.getTime().replace("T", " ")).concat(":00");
                    Timestamp finalDateTimeLocal = Timestamp.valueOf(okDateTimeLocal);


                    if ((Timestamp.valueOf(op.get().getLast_event()).compareTo(finalDateTimeLocal) <= 0)) {


                        realInterrupt.setTime(finalDateTimeLocal);
                        realInterrupt.setMatch(matchService.getMatchById(id).get());

                        this.matchService.changeInterrupt(id, true);

                        this.matchService.addInterrupt(id, realInterrupt);

                        this.interruptService.addInterrupt(realInterrupt);

                        this.matchService.changeLastEvent(id, okDateTimeLocal);
                    }

                }
            }
        }


        if (typeUser == 1)
            return "redirect:/afterlogin";

        return "redirect:/admin";
    }

    @GetMapping("/resume_match")
    public String resume_match(@RequestParam(name="id", required=true) int id, Model model) {

        if (typeUser != 0) {
            Optional<Match> op = this.matchService.getMatchById(id);

            if (op.isPresent())
                model.addAttribute("match", op.get());

            model.addAttribute("resume", new InterruptForm());

            return "resume_match";
        }
        else
            return "redirect:/createData";
    }

    @PostMapping("/resume_match")
    public String resume_match(@RequestParam(name="id", required=true) int id, @ModelAttribute InterruptForm resume) {

        if (!resume.getTime().equals("")) {
            Optional<Match> op = this.matchService.getMatchById(id);
            if (op.isPresent()) {
                Resume realResume = new Resume();

                if ((op.get().isStart()) && !(op.get().isEnd_()) && (op.get().isInterrupted())) {

                    String okDateTimeLocal = (resume.getTime().replace("T", " ")).concat(":00");
                    Timestamp finalDateTimeLocal = Timestamp.valueOf(okDateTimeLocal);

                    if ((Timestamp.valueOf(op.get().getLast_event()).compareTo(finalDateTimeLocal) <= 0)) {

                        realResume.setTime(finalDateTimeLocal);

                        realResume.setMatch(matchService.getMatchById(id).get());

                        this.matchService.changeInterrupt(id, false);

                        this.matchService.addResume(id, realResume);

                        this.resumeService.addResume(realResume);

                        this.matchService.changeLastEvent(id, okDateTimeLocal);

                    }
                }
            }
        }

        if (typeUser == 1)
            return "redirect:/afterlogin";

        return "redirect:/admin";
    }

    public List<Player> excludeReds(int id, List<Player> allplayers) {
        List<Player> playersNoRed = new ArrayList<>();

        for (int i=0; i<allplayers.size(); i++) {
            if (this.redService.getPlayerRedsPerMatch(allplayers.get(i).getId(), id) < 1) {
                playersNoRed.add(allplayers.get(i));
            }
        }
        return playersNoRed;
    }

    @GetMapping("/goal_match")
    public String goal_match(@RequestParam(name="id", required=true) int id, Model model) {

        if (typeUser != 0) {
            Optional<Match> op = this.matchService.getMatchById(id);

            if (op.isPresent()) {
                model.addAttribute("match", op.get());
                int idA = op.get().getTeamA().getId();
                int idB = op.get().getTeamB().getId();

                Optional<List<Player>> match_players = this.playerService.getPlayerfromMatchByTeamId(idA, idB);

                if (match_players.isPresent()) {
                    model.addAttribute("players", excludeReds(id, match_players.get()));
                    model.addAttribute("goal", new GoalForm());
                }
            }

            return "goal_match";
        }
        else
            return "redirect:/createData";
    }

    @PostMapping("/goal_match")
    public String goal_match(@RequestParam(name="id", required=true) int id, @ModelAttribute GoalForm goalf) {

        System.out.println("JOGGGG" + goalf.getPlayer());

        if (!goalf.getTime().equals("") && (goalf.getPlayer() != 0)) {

            Optional<Match> op = this.matchService.getMatchById(id);
            if (op.isPresent()) {
                Goal realGoal = new Goal();

                if ((op.get().isStart()) && !(op.get().isEnd_()) && !(op.get().isInterrupted())) {

                    String okDateTimeLocal = (goalf.getTime().replace("T", " ")).concat(":00");
                    Timestamp finalDateTimeLocal = Timestamp.valueOf(okDateTimeLocal);

                    if ((Timestamp.valueOf(op.get().getLast_event()).compareTo(finalDateTimeLocal) <= 0)) {

                        realGoal.setTime(finalDateTimeLocal);

                        realGoal.setMatch(matchService.getMatchById(id).get());

                        this.matchService.addGoal(id, realGoal);

                        this.matchService.changeLastEvent(id, okDateTimeLocal);

                        Optional<Player> pl = this.playerService.getPlayerById(goalf.getPlayer());

                        if (pl.isPresent()) {
                            realGoal.setPlayer(pl.get());
                            this.goalService.addGoal(realGoal);
                        }
                    }
                }
            }
        }

        if (typeUser == 1)
            return "redirect:/afterlogin";

        return "redirect:/admin";
    }


    @GetMapping("/yellow_match")
    public String yellow_match(@RequestParam(name="id", required=true) int id, Model model) {

        if (typeUser != 0) {
            Optional<Match> op = this.matchService.getMatchById(id);

            if (op.isPresent()) {
                model.addAttribute("match", op.get());
                int idA = op.get().getTeamA().getId();
                int idB = op.get().getTeamB().getId();

                Optional<List<Player>> match_players = this.playerService.getPlayerfromMatchByTeamId(idA, idB);

                if (match_players.isPresent()) {
                    model.addAttribute("players", excludeReds(id, match_players.get()));
                    model.addAttribute("yellow", new GoalForm());
                }
            }
            return "yellow_match";
        }
        else
            return "redirect:/createData";
    }

    @PostMapping("/yellow_match")
    public String yellow_match(@RequestParam(name="id", required=true) int id, @ModelAttribute GoalForm yellowf) {

        if (!yellowf.getTime().equals("") && (yellowf.getPlayer() != 0)) {
            Optional<Match> op = this.matchService.getMatchById(id);
            if (op.isPresent()) {
                YellowCard realYellow = new YellowCard();

                if ((op.get().isStart()) && !(op.get().isEnd_()) && !(op.get().isInterrupted())) {

                    String okDateTimeLocal = (yellowf.getTime().replace("T", " ")).concat(":00");
                    Timestamp finalDateTimeLocal = Timestamp.valueOf(okDateTimeLocal);

                    if ((Timestamp.valueOf(op.get().getLast_event()).compareTo(finalDateTimeLocal) <= 0)) {

                        Optional<Player> pl = this.playerService.getPlayerById(yellowf.getPlayer());

                        if (pl.isPresent()) {

                            int num_yellows = this.yellowService.getPlayerYellowsPerMatch(pl.get().getId(), id);

                            realYellow.setPlayer(pl.get());

                            realYellow.setTime(finalDateTimeLocal);

                            realYellow.setMatch(matchService.getMatchById(id).get());

                            this.yellowService.addYellow(realYellow);

                            this.matchService.addYellow(id, realYellow);

                            this.matchService.changeLastEvent(id, okDateTimeLocal);

                            if (num_yellows == 1) {
                                RedCard realRed = new RedCard();
                                realRed.setPlayer(pl.get());
                                realRed.setMatch(matchService.getMatchById(id).get());
                                realRed.setTime(finalDateTimeLocal);

                                this.redService.addRed(realRed);

                                this.matchService.addRed(id, realRed);
                            }

                        }

                    }
                }
            }
        }



        if (typeUser == 1)
            return "redirect:/afterlogin";

        return "redirect:/admin";
    }

    @GetMapping("/red_match")
    public String red_match(@RequestParam(name="id", required=true) int id, Model model) {

        if (typeUser != 0) {
            Optional<Match> op = this.matchService.getMatchById(id);

            if (op.isPresent()) {
                model.addAttribute("match", op.get());
                int idA = op.get().getTeamA().getId();
                int idB = op.get().getTeamB().getId();

                Optional<List<Player>> match_players = this.playerService.getPlayerfromMatchByTeamId(idA, idB);

                if (match_players.isPresent()) {
                    model.addAttribute("players", excludeReds(id, match_players.get()));
                    model.addAttribute("red", new GoalForm());
                }
            }

            return "red_match";
        }
        else
            return "redirect:/createData";

    }

    @PostMapping("/red_match")
    public String red_match(@RequestParam(name="id", required=true) int id, @ModelAttribute GoalForm redf) {

        if (!redf.getTime().equals("") && (redf.getPlayer() != 0)) {
            Optional<Match> op = this.matchService.getMatchById(id);
            if (op.isPresent()) {
                RedCard realRed = new RedCard();

                if ((op.get().isStart()) && !(op.get().isEnd_()) && !(op.get().isInterrupted())) {

                    String okDateTimeLocal = (redf.getTime().replace("T", " ")).concat(":00");
                    Timestamp finalDateTimeLocal = Timestamp.valueOf(okDateTimeLocal);

                    if ((Timestamp.valueOf(op.get().getLast_event()).compareTo(finalDateTimeLocal) <= 0)) {

                        Optional<Player> pl = this.playerService.getPlayerById(redf.getPlayer());

                        if (pl.isPresent()) {
                            realRed.setPlayer(pl.get());

                            realRed.setTime(finalDateTimeLocal);
                            realRed.setMatch(matchService.getMatchById(id).get());

                            this.redService.addRed(realRed);

                            this.matchService.addRed(id, realRed);

                            this.matchService.changeLastEvent(id, okDateTimeLocal);

                        }

                    }
                }
            }
        }




        if (typeUser == 1)
            return "redirect:/afterlogin";

        return "redirect:/admin";
    }

    @GetMapping("/listteam")
    public String listTeams(Model model) {
        if (typeUser == 2) {
            model.addAttribute("team", this.teamService.getAllTeams());
            return "listteam";
        }
        else
            return "redirect:/createData";
    }

    @GetMapping("/editteam")
    public String editTeam(@RequestParam(name="id", required=true) int id, Model m) {

        if (typeUser == 2) {
            Optional<Team> op = this.teamService.getTeamById(id);
            if (op.isPresent()) {
                m.addAttribute("team", op.get());
                return "editteam";
            }
            else {
                return "redirect:/listteam";
            }
        }
        else
            return "redirect:/createData";
    }

    @PostMapping("/saveteam")
    public String saveTeam(@ModelAttribute Team team) {

        if ((!team.getName().equals("")) && (!team.getImage().equals(""))) {
            this.teamService.addTeam(team);
        }

        return "redirect:/listteam";
    }

    @GetMapping("/listusers")
    public String listUsers(Model model) {
        if (typeUser == 2) {
            model.addAttribute("user", this.userService.getAllUsers());
            return "listusers";
        }
        else
            return "redirect:/createData";
    }

    @GetMapping("/edituser")
    public String editUser(@RequestParam(name="email", required=true) String email, Model m) {

        if (typeUser == 2) {
            Optional<Users> op = this.userService.getUserByEmail(email);
            if (op.isPresent()) {
                m.addAttribute("user", op.get());
                return "edituser";
            }
            else {
                return "redirect:/listusers";
            }
        }
        else
            return "redirect:/createData";
    }

    @PostMapping("/saveuser")
    public String saveStudent(@ModelAttribute Users user) {
        System.out.println("FINAL  " + user.isAdmin());

        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        System.out.println(user.getTelephone());
        System.out.println(user.getName());

        if ((!user.getEmail().equals("")) && (!user.getPassword().equals("")) && (!user.getName().equals("")) && (!user.getTelephone().equals(""))) {
            this.userService.addUser(user);
        }
        return "redirect:/listusers";
    }

    @GetMapping("/listmatch")
    public String listMatches(Model model) {
        if (typeUser == 2) {
            model.addAttribute("match", this.matchService.getAllMatches());
            return "listmatch";
        }
        else
            return "redirect:/createData";
    }

    @GetMapping("/editmatch")
    public String editMatch(@RequestParam(name="id", required=true) int id, Model m) {

        if (typeUser == 2) {
            Optional<Match> op = this.matchService.getMatchById(id);
            if (op.isPresent()) {

                MatchForm mf = new MatchForm();

                mf.setId(op.get().getId());

                m.addAttribute("match", mf);
                return "editmatch";
            }
            else {
                return "redirect:/listmatch";
            }
        }
        else
            return "redirect:/createData";
    }

    @PostMapping("/savematch")
    public String saveMatch(@RequestParam(name="id", required=true) int id, @ModelAttribute MatchForm matchf) {

        Optional<Match> op = this.matchService.getMatchById(id);
        if ((op.isPresent()) && (!matchf.getDatetime().equals("")) && (!matchf.getLocation().equals(""))) {
            op.get().setLocation(matchf.getLocation());

            String okDateTimeLocal = (matchf.getDatetime().replace("T", " ")).concat(":00");
            Timestamp finalDateTimeLocal = Timestamp.valueOf(okDateTimeLocal);

            op.get().setDate(finalDateTimeLocal);

            this.matchService.addMatch(op.get());
        }

        return "redirect:/listmatch";
    }

    @GetMapping("/listplayer")
    public String listPlayers(Model model) {
        if (typeUser == 2) {
            model.addAttribute("player", this.playerService.getAllPlayers());
            return "listplayer";
        }
        else
            return "redirect:/createData";
    }

    @GetMapping("/editplayer")
    public String editPlayer(@RequestParam(name="id", required=true) int id, Model m) {

        if (typeUser == 2) {
            Optional<Player> op = this.playerService.getPlayerById(id);
            if (op.isPresent()) {

                PlayerForm pf = new PlayerForm();
                pf.setId(op.get().getId());

                m.addAttribute("player", pf);
                m.addAttribute("allteams", this.teamService.getAllTeams());
                return "editplayer";
            }
            else {
                return "redirect:/listplayer";
            }
        }
        else
            return "redirect:/createData";
    }

    @PostMapping("/saveplayer")
    public String savePlayer(@RequestParam(name="id", required=true) int id, PlayerForm pf) throws ParseException {
        Optional<Player> op = this.playerService.getPlayerById(id);
        if ((op.isPresent()) && (!pf.getName().equals("")) && (pf.getT() != null) && (!pf.getBirth().equals("")) && (!pf.getPosition().equals(""))) {
            op.get().setName(pf.getName());
            op.get().setT(pf.getT());
            op.get().setPosition(pf.getPosition());

            Date birth = new SimpleDateFormat("yyyy-MM-dd").parse(pf.getBirth());

            op.get().setBirth(birth);

            this.playerService.addPlayer(op.get());

        }

        return "redirect:/listplayer";
    }

    @GetMapping("/deleteuser")
    public String deleteUser(@RequestParam(name="email", required=true) String email, Model m) {

        if (typeUser == 2) {
            Optional<Users> op = this.userService.getUserByEmail(email);
            if (op.isPresent()) {
                this.userService.deleteUser(email);

            }
            return "redirect:/listusers";

        }
        else
            return "redirect:/createData";
    }

    @GetMapping("/deletematch")
    public String deleteMatch(@RequestParam(name="id", required=true) int id, Model m) {

        if (typeUser == 2) {
            Optional<Match> op = this.matchService.getMatchById(id);
            if (op.isPresent()) {
                if (!op.get().isStart())  this.matchService.deleteMatch(id);

            }
            return "redirect:/listmatch";

        }
        else
            return "redirect:/createData";
    }

   }