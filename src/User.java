import java.util.LinkedHashMap;
import java.util.Map;

public class User {
    private String name;
    private Map<String, Integer> contests;

    public User(String name) {
        this.name = name;
        this.contests = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getContests() {
        return contests;
    }


    public int getTotalPoints() {
        return this.contests.values().stream().mapToInt(Integer::intValue).sum();
    }
}