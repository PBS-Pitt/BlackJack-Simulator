

public class BlackjackCards extends RandIndexQueue<Card> {

    public BlackjackCards(int sz) {
        super(sz);
        // TODO Auto-generated constructor stub
    }

    public int getValue(){
        int handResult = 0;
        boolean checkValue;
        boolean forLoop = true;
        boolean forLoop2 = true;
        for (int i = 0; i < size() && forLoop; i ++){    
            Card item = get(i);
            handResult += item.value(); 
            checkValue = false;
            while (!checkValue){
                if (handResult < 17){
                    checkValue = true;
                } else {
                    if (handResult <= 21){
                        checkValue = true;
                    } else {
                        if (item.value() == 11){
                            handResult -= 10;
                            forLoop2 = false;
                        } else {
                            int count = 0;
                            for (int j = 1; j < size() && forLoop2; j++){
                                int position = i - j;
                                Card item2 = get(position);
                                if (item2.value() == 11){
                                    count++;
                                    forLoop2 = false;
                                }
                            }
                            forLoop2 = false;
                            if (count == 1){
                                handResult -= 10;
                            } else {
                                checkValue = true;
                                forLoop = false;
                            }
                        }
                    }
                }
            }
            
        }
        return handResult;
    }
    
}
