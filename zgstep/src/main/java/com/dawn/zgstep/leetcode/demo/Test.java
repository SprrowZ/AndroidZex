package com.dawn.zgstep.leetcode.demo;

public class Test {

    public static void main(String[] args){
        romanToInt("III");
    }
    public static int romanToInt(String s) {
        //char[] charNum = new char[]{'I','V','X','L','C','D','M'};
        String numStr = "IVXLCDM";
        String[] targetArr = new String[s.length()];
        int pos = 0;
        int count = 0;
        for(int i=0;i<s.length()-1;i++){
            char firstChar = s.charAt(i);
            char nextChar = s.charAt(i+1);
            if(numStr.indexOf(firstChar)< numStr.indexOf(nextChar)){//说明是两个一体的
                targetArr[pos++] = firstChar+nextChar+"";
                ++i;
                count = count+2;
            }else{
                targetArr[pos++] = firstChar+"";
                count = count+1;
            }
        }
        if(count!=s.length()){
            targetArr[pos] = s.charAt(s.length()-1)+"";
        }

        int result = 0;
        for(int i= 0;i<targetArr.length;i++){
            if(targetArr[i]==null) break;
            switch(targetArr[i]){
                case "I":
                    result = result +1;
                case "V":
                    result = result +5;
                case "X":
                    result = result +10;
                case "L":
                    result = result +50;
                case "C":
                    result = result+100;
                case "D":
                    result = result+500;
                case "M":
                    result = result+1000;
                case "IV":
                    result = result +4;
                case "IX":
                    result = result +9;
                case "XL":
                    result = result+40;
                case "XC":
                    result = result+90;
                case "CD":
                    result = result +400;
                case "CM":
                    result =result +900;
                default:
                    result =result;
            }
        }
        return result;
    }
}
