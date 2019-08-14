package ybq.android.myapplication;


public class Bspatch {

    public static  native void bsPatch(String oldPath ,String newPath ,String patchPath);

    static {
        System.loadLibrary("native-lib");
    }

}
