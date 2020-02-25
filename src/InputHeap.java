import java.util.ArrayList;
import java.util.List;

public class InputHeap {

    //    private String[] inputTokens;
    private ArrayList<String> inputHeap;    //ArrayList of Strings

//    public InputHeap(String input){
//        StringTokenizer inputTokenizer = new StringTokenizer(input);
//        inputTokens = new String[inputTokenizer.countTokens()];
//        for(int i = inputTokens.length - 1; i >= 0; i--){
//            inputTokens[i] = inputTokenizer.nextToken();
//        }
//        size = inputTokens.length;
//    }

    public InputHeap(String input, String delimiters) {
        String[] arr = input.split(delimiters);
        String in = stringArrayToString(arr);
        inputHeap = new ArrayList<String>();
        while (in.length() > 0) {   //add in to the inputTokens, removing the last word each time
            inputHeap.add(in);
            if (in.contains(" ")) {
                in = in.substring(0, in.lastIndexOf(" "));
            } else {
                in = "";
            }
        }
    }

    //removes a given length from the start of each String, removing the String if the String is too short
    public void removeFromEach(int lengthToRemove) {
        for (int i = inputHeap.size() - 1; i >= 0; i--) {
            String currentString = inputHeap.get(i);
            if (currentString.length() <= lengthToRemove) {
                inputHeap.remove(i);
            } else {
                inputHeap.set(i, currentString.substring(lengthToRemove));
            }
        }
    }

    public String getLastElement(){
        return inputHeap.get(inputHeap.size()-1);
    }

    public boolean hasMoreElements(){
        return inputHeap.size() > 0;
    }

    public List toList() {
        return new ArrayList<String>(inputHeap);
    }

    //converts a String[] to a space-separated list of its elements. No extra spaces at start or end.
    private static String stringArrayToString(String[] arr) {
        if (arr.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(" ");
            sb.append(s);
        }
        return sb.toString().substring(1);  //removes the space at the front
    }
//    //pops from end of list
//    public String pop(){
//        size--;
//        return inputTokens[size];   //ordinarily would be size-1 but size was updated so new size is same as last index
//    }
//
//    //pushes to end of list
//    public void push(String token){
//        inputTokens[size] = token;
//        size++;
//    }

}
