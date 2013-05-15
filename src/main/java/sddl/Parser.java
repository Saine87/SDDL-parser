package sddl;

/**
 * User: saine
 * Date: 5/15/13
 * Time: 10:51 AM
 */
public class Parser {

    public static void main (String[] args){

        try {

            Sddl sddl = new Sddl("D:P(A;;GA;;;SY)(A;;GRGWGX;;;BA)(A;;GRGWGX;;;WD)(A;;GRGX;;;RC)");
            System.out.println(sddl.parse());

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
