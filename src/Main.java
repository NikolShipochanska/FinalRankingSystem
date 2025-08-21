import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Map<String,String> contests = new LinkedHashMap<>();
        Map<String,User> submissions = new TreeMap<>();

        String input = scanner.nextLine();

        while(!input.equals("end of contests")){
            String[] inputData = input.split(":");
            String contestName = inputData[0];
            String password = inputData[1];
            contests.put(contestName,password);
            input = scanner.nextLine();
        }

        String data = scanner.nextLine();

        while(!data.equals("end of submissions")){
            String[] dataInfo = data.split("=>");
            String contestName = dataInfo[0];
            String password = dataInfo[1];
            String username = dataInfo[2];
            int points = Integer.parseInt(dataInfo[3]);

            boolean correctPassword = password.equals(contests.get(contestName));
            boolean sameUserAndContest = submissions.containsKey(username) && submissions.get(username).getContests().containsKey(contestName);

            if(contests.containsKey(contestName)){
                if(correctPassword){
                    if(!submissions.containsKey(username)) {
                        User user = new User(username);
                        submissions.put(username, user);
                        submissions.get(username).getContests().put(contestName, points);
                    }else if(sameUserAndContest){
                        int oldPoints = submissions.get(username).getContests().get(contestName);
                        if(oldPoints<points){
                            submissions.get(username).getContests().replace(contestName,oldPoints,points);
                        }
                    }else{
                        submissions.get(username).getContests().put(contestName,points);
                    }
                }
            }

            data = scanner.nextLine();
        }

        User bestUser = submissions.values().stream()
                .max(Comparator.comparingInt(User::getTotalPoints))
                .orElse(null);

        if (bestUser != null) {
            System.out.printf("Best candidate is %s with total %d points.%n",
                    bestUser.getName(), bestUser.getTotalPoints());
        }


        System.out.println("Ranking:");
        for(Map.Entry<String,User> entry : submissions.entrySet()){
            System.out.println(entry.getKey());
            entry.getValue().getContests().entrySet().stream()
                    .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                    .forEach(e-> System.out.printf("# %s -> %d\n", e.getKey(), e.getValue()));
        }
    }
}