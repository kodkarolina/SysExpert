import java.util.Map;

public class MicroControllerModelRule {

    private int rule_id;
    private Map<String, Object[]> rules;

    public MicroControllerModelRule(int rule_id, Map<String, Object[]> rules){
        this.rule_id = rule_id;
        this.rules = rules;
    }

    public int getRule_id() {
        return rule_id;
    }

    public Map<String, Object[]> getRules() {
        return rules;
    }
}
