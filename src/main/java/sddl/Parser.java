package sddl;

/**
 * User: saine
 * Date: 5/15/13
 * Time: 10:51 AM
 */
public class Parser {

    public static void main (String[] args){

        try {

            for(String i: args){
                Sddl sddl = new Sddl(i);
                System.out.println(sddl.parse());
            }

        } catch (Sddl.NullACLsInSddl nullACLsInSddl) {
            nullACLsInSddl.printStackTrace();
        } catch (Sddl.UnSupportedTagInSddl unSupportedTagInSddl) {
            unSupportedTagInSddl.printStackTrace();
        } catch (Sddl.InvalidTokenInSddl invalidTokenInSddl) {
            invalidTokenInSddl.printStackTrace();
        } catch (Sddl.IllegalFormatSddl illegalFormatSddl) {
            illegalFormatSddl.printStackTrace();
        }

    }

}
