package ru.chistov.notes.repository;


import java.util.Random;

import ru.chistov.notes.R;

public class PictureIndexConverter {
    private static Random rnd = new Random();
    private static  Object syncObj = new Object();
    private static  int[] picIndex = {
            R.color.purple_500,
            R.color.teal_700,
            R.color.purple_700,
            R.color.teal_200,
    };
    public static int randomPictureIndex(){
        synchronized (syncObj){
            return rnd.nextInt(picIndex.length);
        }
    }
    public static int getPictureByIndex(int index){
        if (index < 0 || index >= picIndex.length){
            index = 0;
        }
        return picIndex[index];
    }
    public static int getIndexByPicture(int picture){
        for(int i = 0; i < picIndex.length; i++){
            if (picIndex[i] == picture){
                return i;
            }
        }
        return 0;
    }

}
