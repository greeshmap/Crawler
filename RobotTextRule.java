
public class RobotTextRule
{
    public String userAgent;
    public String rule;

    RobotTextRule() {}
    @Override 
    public String toString() 
    {
        StringBuilder result = new StringBuilder();
        result.append(this.getClass().getName() + System.getProperty("line.separator"));
        result.append("   userAgent: " + this.userAgent + System.getProperty("line.separator"));
        result.append("   rule: " + this.rule + System.getProperty("line.separator"));
        return result.toString();
    }    
}