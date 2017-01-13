/*
 * Programme: a Top Trumps game featuring a GUI and a database connection to 
 * store data.
 * 
 * Class: represents a top trumps card, with a description and five attribute 
 * values.
 * 
 * @author Team A
 *         Faisal Ahsan 2242114a 
 *         Aidan Butler 2281611b 
 *         Stewart Brown 2276998b
 *         Jane Kennedy 2287767k 
 *         Svetoslava Nikolova 1004630n
 */
public class Card {
    
    private final String nameVal;
    private final int attri1Val;
    private final int attri2Val;
    private final int attri3Val;
    private final int attri4Val;
    private final int attri5Val;

    public Card(String name, int attri1, int attri2, int attri3, 
            int attri4, int attri5) {
        
        this.nameVal = name;
        this.attri1Val = attri1;
        this.attri2Val = attri2;
        this.attri3Val = attri3;
        this.attri4Val = attri4;
        this.attri5Val = attri5;
    }

    /**
     * Returns the Card's name, a String.
     * 
     * @return the Card's name, String 
     */
    public String getNameVal() {
        
        return nameVal;
    }

    /**
     * Returns the value of the Card's first attribute.
     * 
     * @return value of first attribute, int
     */
    private int getAttri1Val() {

        return attri1Val;
    }

    /**
     * Returns the value of the Card's second attribute.
     * 
     * @return value of second attribute, int
     */    
    private int getAttri2Val() {

        return attri2Val;
    }

    /**
     * Returns the value of the Card's third attribute.
     * 
     * @return value of third attribute, int
     */    
    private int getAttri3Val() {

        return attri3Val;
    }

    /**
     * Returns the value of the Card's third attribute.
     * 
     * @return value of third attribute, int
     */    
    private int getAttri4Val() {

        return attri4Val;
    }

    /**
     * Returns the value of the Card's fourth attribute.
     * 
     * @return value of fourth attribute, int
     */    
    private int getAttri5Val() {

        return attri5Val;
    }
    /**
     * Returns the value of a Card's attribute with a given index.
     * 
     * @param index, int
     * @return the value of that attribute, int
     */
    public int getAttriValAtIndex(int index) {
        
        switch (index) {
            
            case 1:
                
                return getAttri1Val();

            case 2:

                return getAttri2Val();

            case 3:

                return getAttri3Val();

            case 4:

                return getAttri4Val();

            case 5:

                return getAttri5Val();

            default:

                return -1;
        }
    }
 
}
