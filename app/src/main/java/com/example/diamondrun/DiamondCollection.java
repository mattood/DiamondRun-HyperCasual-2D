package com.example.diamondrun;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.LinkedList;

public class DiamondCollection<num> {
    //collection of Diamond object / connectors
    private LinkedList<Diamond> diamonds;
    private int diamondCollectionYSpeed = 5; //arbitrary
    GameGrid gameGrid;
    boolean initShatterDiamonds = false;
    public LinkedList<Bitmap> bitmaps;
    private int topOfScreen = 0;
    private int bottomOfScreen;
    private int bitmapWidth = 0;
    private int numDiamonds;
    private int bitmapHeight = 150;
    private int collectionYLocation;
    private boolean initDiamondBitmap = true;
    private boolean initGreenDiamonds, initRedDiamonds, initPurpleDiamonds, initYellowDiamonds, initBlueDiamonds = false;
    private Rect rect;
    private Bitmap bitmap;
    private Bitmap[] shatterRed = new Bitmap[26];
    private Bitmap[] shatterGreen = new Bitmap[26];
    private Bitmap[] shatterPurple = new Bitmap[26];
    private Bitmap[] shatterYellow = new Bitmap[26];
    private Bitmap[] shatterBlue = new Bitmap[26];
    public boolean startShatter = false;
    public ArrayList<Bitmap> arrGreenBitmaps = new ArrayList<>();
    public ArrayList<Bitmap> arrRedBitmaps = new ArrayList<>();
    public ArrayList<Bitmap> arrPurpleBitmaps = new ArrayList<>();
    public ArrayList<Bitmap> arrYellowBitmaps = new ArrayList<>();
    public ArrayList<Bitmap> arrBlueBitmaps = new ArrayList<>();
    public int maxShatterIndex = shatterRed.length-1;
    public int count, vFlap, idCurrentBitmap;

    DiamondCollection(Context context, int numDiamonds, int screenWidth, int screenHeight) {
        bitmapWidth = screenWidth / numDiamonds;
        this.numDiamonds = numDiamonds;
        bottomOfScreen = screenHeight;
        gameGrid = new GameGrid(numDiamonds, screenWidth);
        diamonds = new LinkedList<Diamond>();//initializing DiamondCollection
        bitmaps = new LinkedList<Bitmap>();
        initShatterDiamondBitmaps(context);
        spawnDiamonds(context, numDiamonds);
        this.count = 0;
        this.vFlap = 5;
        this.idCurrentBitmap = 0;
    }

    public void initShatterDiamondBitmaps(Context context) {
        //red init
        shatterRed[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt1), bitmapWidth, bitmapHeight, true);
        shatterRed[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt2), bitmapWidth, bitmapHeight, true);
        shatterRed[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt3), bitmapWidth, bitmapHeight, true);
        shatterRed[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt4), bitmapWidth, bitmapHeight, true);
        shatterRed[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt5), bitmapWidth, bitmapHeight, true);
        shatterRed[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt6), bitmapWidth, bitmapHeight, true);
        shatterRed[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt7), bitmapWidth, bitmapHeight, true);
        shatterRed[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt8), bitmapWidth, bitmapHeight, true);
        shatterRed[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt9), bitmapWidth, bitmapHeight, true);
        shatterRed[9] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt10), bitmapWidth, bitmapHeight, true);
        shatterRed[10] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt11), bitmapWidth, bitmapHeight, true);
        shatterRed[11] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt12), bitmapWidth, bitmapHeight, true);
        shatterRed[12] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt13), bitmapWidth, bitmapHeight, true);
        shatterRed[13] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt14), bitmapWidth, bitmapHeight, true);
        shatterRed[14] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt15), bitmapWidth, bitmapHeight, true);
        shatterRed[15] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt16), bitmapWidth, bitmapHeight, true);
        shatterRed[16] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt17), bitmapWidth, bitmapHeight, true);
        shatterRed[17] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt18), bitmapWidth, bitmapHeight, true);
        shatterRed[18] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt19), bitmapWidth, bitmapHeight, true);
        shatterRed[19] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt20), bitmapWidth, bitmapHeight, true);
        shatterRed[20] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt21), bitmapWidth, bitmapHeight, true);
        shatterRed[21] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt22), bitmapWidth, bitmapHeight, true);
        shatterRed[22] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt23), bitmapWidth, bitmapHeight, true);
        shatterRed[23] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt24), bitmapWidth, bitmapHeight, true);
        shatterRed[24] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt25), bitmapWidth, bitmapHeight, true);
        shatterRed[25] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondredpt26), bitmapWidth, bitmapHeight, true);


        //green init
        shatterGreen[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt1), bitmapWidth, bitmapHeight, true);
        shatterGreen[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt2), bitmapWidth, bitmapHeight, true);
        shatterGreen[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt3), bitmapWidth, bitmapHeight, true);
        shatterGreen[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt4), bitmapWidth, bitmapHeight, true);
        shatterGreen[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt5), bitmapWidth, bitmapHeight, true);
        shatterGreen[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt6), bitmapWidth, bitmapHeight, true);
        shatterGreen[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt7), bitmapWidth, bitmapHeight, true);
        shatterGreen[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt8), bitmapWidth, bitmapHeight, true);
        shatterGreen[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt9), bitmapWidth, bitmapHeight, true);
        shatterGreen[9] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt10), bitmapWidth, bitmapHeight, true);
        shatterGreen[10] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt11), bitmapWidth, bitmapHeight, true);
        shatterGreen[11] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt12), bitmapWidth, bitmapHeight, true);
        shatterGreen[12] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt13), bitmapWidth, bitmapHeight, true);
        shatterGreen[13] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt14), bitmapWidth, bitmapHeight, true);
        shatterGreen[14] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt15), bitmapWidth, bitmapHeight, true);
        shatterGreen[15] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt16), bitmapWidth, bitmapHeight, true);
        shatterGreen[16] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt17), bitmapWidth, bitmapHeight, true);
        shatterGreen[17] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt18), bitmapWidth, bitmapHeight, true);
        shatterGreen[18] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt19), bitmapWidth, bitmapHeight, true);
        shatterGreen[19] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt20), bitmapWidth, bitmapHeight, true);
        shatterGreen[20] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt21), bitmapWidth, bitmapHeight, true);
        shatterGreen[21] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt22), bitmapWidth, bitmapHeight, true);
        shatterGreen[22] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt23), bitmapWidth, bitmapHeight, true);
        shatterGreen[23] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt24), bitmapWidth, bitmapHeight, true);
        shatterGreen[24] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt25), bitmapWidth, bitmapHeight, true);
        shatterGreen[25] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondgreenpt26), bitmapWidth, bitmapHeight, true);

        //purple init
        shatterPurple[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept1), bitmapWidth, bitmapHeight, true);
        shatterPurple[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept2), bitmapWidth, bitmapHeight, true);
        shatterPurple[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept3), bitmapWidth, bitmapHeight, true);
        shatterPurple[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept4), bitmapWidth, bitmapHeight, true);
        shatterPurple[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept5), bitmapWidth, bitmapHeight, true);
        shatterPurple[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept6), bitmapWidth, bitmapHeight, true);
        shatterPurple[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept7), bitmapWidth, bitmapHeight, true);
        shatterPurple[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept8), bitmapWidth, bitmapHeight, true);
        shatterPurple[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept9), bitmapWidth, bitmapHeight, true);
        shatterPurple[9] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept10), bitmapWidth, bitmapHeight, true);
        shatterPurple[10] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept11), bitmapWidth, bitmapHeight, true);
        shatterPurple[11] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept12), bitmapWidth, bitmapHeight, true);
        shatterPurple[12] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept13), bitmapWidth, bitmapHeight, true);
        shatterPurple[13] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept14), bitmapWidth, bitmapHeight, true);
        shatterPurple[14] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept15), bitmapWidth, bitmapHeight, true);
        shatterPurple[15] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept16), bitmapWidth, bitmapHeight, true);
        shatterPurple[16] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept17), bitmapWidth, bitmapHeight, true);
        shatterPurple[17] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept18), bitmapWidth, bitmapHeight, true);
        shatterPurple[18] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept19), bitmapWidth, bitmapHeight, true);
        shatterPurple[19] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept20), bitmapWidth, bitmapHeight, true);
        shatterPurple[20] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept21), bitmapWidth, bitmapHeight, true);
        shatterPurple[21] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept22), bitmapWidth, bitmapHeight, true);
        shatterPurple[22] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept23), bitmapWidth, bitmapHeight, true);
        shatterPurple[23] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept24), bitmapWidth, bitmapHeight, true);
        shatterPurple[24] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept25), bitmapWidth, bitmapHeight, true);
        shatterPurple[25] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondpurplept26), bitmapWidth, bitmapHeight, true);

        //yellow init
        shatterYellow[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt1), bitmapWidth, bitmapHeight, true);
        shatterYellow[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt2), bitmapWidth, bitmapHeight, true);
        shatterYellow[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt3), bitmapWidth, bitmapHeight, true);
        shatterYellow[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt4), bitmapWidth, bitmapHeight, true);
        shatterYellow[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt5), bitmapWidth, bitmapHeight, true);
        shatterYellow[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt6), bitmapWidth, bitmapHeight, true);
        shatterYellow[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt7), bitmapWidth, bitmapHeight, true);
        shatterYellow[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt8), bitmapWidth, bitmapHeight, true);
        shatterYellow[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt9), bitmapWidth, bitmapHeight, true);
        shatterYellow[9] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt10), bitmapWidth, bitmapHeight, true);
        shatterYellow[10] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt11), bitmapWidth, bitmapHeight, true);
        shatterYellow[11] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt12), bitmapWidth, bitmapHeight, true);
        shatterYellow[12] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt13), bitmapWidth, bitmapHeight, true);
        shatterYellow[13] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt14), bitmapWidth, bitmapHeight, true);
        shatterYellow[14] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt15), bitmapWidth, bitmapHeight, true);
        shatterYellow[15] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt16), bitmapWidth, bitmapHeight, true);
        shatterYellow[16] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt17), bitmapWidth, bitmapHeight, true);
        shatterYellow[17] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt18), bitmapWidth, bitmapHeight, true);
        shatterYellow[18] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt19), bitmapWidth, bitmapHeight, true);
        shatterYellow[19] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt20), bitmapWidth, bitmapHeight, true);
        shatterYellow[20] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt21), bitmapWidth, bitmapHeight, true);
        shatterYellow[21] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt22), bitmapWidth, bitmapHeight, true);
        shatterYellow[22] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt23), bitmapWidth, bitmapHeight, true);
        shatterYellow[23] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt24), bitmapWidth, bitmapHeight, true);
        shatterYellow[24] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt25), bitmapWidth, bitmapHeight, true);
        shatterYellow[25] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondyellowpt26), bitmapWidth, bitmapHeight, true);

        //blue init

        shatterBlue[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept1), bitmapWidth, bitmapHeight, true);
        shatterBlue[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept2), bitmapWidth, bitmapHeight, true);
        shatterBlue[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept3), bitmapWidth, bitmapHeight, true);
        shatterBlue[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept4), bitmapWidth, bitmapHeight, true);
        shatterBlue[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept5), bitmapWidth, bitmapHeight, true);
        shatterBlue[5] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept6), bitmapWidth, bitmapHeight, true);
        shatterBlue[6] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept7), bitmapWidth, bitmapHeight, true);
        shatterBlue[7] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept8), bitmapWidth, bitmapHeight, true);
        shatterBlue[8] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept9), bitmapWidth, bitmapHeight, true);
        shatterBlue[9] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept10), bitmapWidth, bitmapHeight, true);
        shatterBlue[10] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept11), bitmapWidth, bitmapHeight, true);
        shatterBlue[11] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept12), bitmapWidth, bitmapHeight, true);
        shatterBlue[12] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept13), bitmapWidth, bitmapHeight, true);
        shatterBlue[13] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept14), bitmapWidth, bitmapHeight, true);
        shatterBlue[14] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept15), bitmapWidth, bitmapHeight, true);
        shatterBlue[15] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept16), bitmapWidth, bitmapHeight, true);
        shatterBlue[16] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept17), bitmapWidth, bitmapHeight, true);
        shatterBlue[17] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept18), bitmapWidth, bitmapHeight, true);
        shatterBlue[18] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept19), bitmapWidth, bitmapHeight, true);
        shatterBlue[19] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept20), bitmapWidth, bitmapHeight, true);
        shatterBlue[20] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept21), bitmapWidth, bitmapHeight, true);
        shatterBlue[21] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept22), bitmapWidth, bitmapHeight, true);
        shatterBlue[22] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept23), bitmapWidth, bitmapHeight, true);
        shatterBlue[23] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept24), bitmapWidth, bitmapHeight, true);
        shatterBlue[24] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept25), bitmapWidth, bitmapHeight, true);
        shatterBlue[25] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.shatteringdiamondbluept26), bitmapWidth, bitmapHeight, true);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Diamond get_at(int index) {
        return diamonds.get(index);
    }

    public Rect getRect(int i) { //pass in index upon call
        int bitmapHeight = this.get_at(i).getBitmapHeight();

        rect = new Rect((int) this.get_at(i).getXLocation(), (int) this.get_at(i).getYLocation(),
                (int) this.get_at(i).getXLocation() + this.bitmapWidth,
                (int) this.get_at(i).getYLocation() + bitmapHeight);
        return rect;
    }

    private void setXLocation() {
        int bitmapWidth = diamonds.getFirst().getBitmapWidth();

        for (int i = 0; i < diamonds.size(); i++) {
            diamonds.get(i).setXLocation(gameGrid.getDiamondXposition(bitmapWidth, i));
        }
    }

    public GameGrid getGameGrid() {
        return this.gameGrid;
    }

    public int getYLocation(int index) {
        for (int i = 0; i < diamonds.size(); i++) { //going through entire linked list
            collectionYLocation = diamonds.get(i).getYLocation();
        }
        return collectionYLocation;
    }

    public void resetYLocation() {
        for (int i = 0; i < diamonds.size(); i++) {
            diamonds.get(i).setYLocation(topOfScreen - bitmapHeight);
        }
    }

    public Bitmap getBitmapAtIndex(int i) {
        return this.bitmaps.get(i);
    }

    public int getXLocation(int index) {
        return get_at(index).getXLocation();
    }

    public void spawnDiamonds(Context context, int numDiamonds) {
        for (int i = 0; i < numDiamonds; i++) { //we setting the size of entire collection here where they are created
            Diamond d = new Diamond(context, bitmapWidth, bitmapHeight);
            d.setYLocation(topOfScreen - d.getBitmapHeight());
            diamonds.add(d);
        }
        setXLocation();
    }

    public void moveDiamondsDownScreen() {
        for (int i = 0; i < diamonds.size(); i++) { //going through entire linked list
            diamonds.get(i).setYLocation(diamonds.get(i).getYLocation() + this.diamondCollectionYSpeed);
        }
    }

    public boolean diamondCollectionMovedBelowScreen() {
        for (int i = 0; i < numDiamonds; i++) { //axis length is number of diamonds
            if (this.getYLocation(1) > bottomOfScreen) { //all index have same y location so using 1
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public Bitmap getBitmapAnimated(ArrayList<Bitmap> arrBitmaps) { //animate shatter
        count++;
        if (this.count == this.vFlap) {
            for (int i = 0; i < arrBitmaps.size(); i++) {
                if (i == arrBitmaps.size() - 1) {
                    this.idCurrentBitmap = 0;
                    break;
                } else if (this.idCurrentBitmap == i) {
                    idCurrentBitmap = i + 1;
                    break;
                }
            }
            count = 0;

        }
        return arrBitmaps.get(idCurrentBitmap);
    }

    public void createDiamondShatterAnimator(int i) {
        this.startShatter = true; //gets set true in updated

        if(!initRedDiamonds) { //preventing duplicates
            for(int r = 0; r < shatterRed.length; r++){
                this.arrRedBitmaps.add(shatterRed[r]); //adding all 26 shatter red bitmaps into the arraylist
            }
            this.initRedDiamonds = true;
        }

        if(!initGreenDiamonds) { //preventing duplicates
            for(int g = 0; g < shatterGreen.length; g++){
                this.arrGreenBitmaps.add(shatterGreen[g]); //adding all 26 shatter green bitmaps into the arraylist
            }
            this.initGreenDiamonds = true;
        }

        if(!initPurpleDiamonds){//preventing duplicates
            for(int p = 0; p < shatterPurple.length; p++){
                this.arrPurpleBitmaps.add(shatterPurple[p]); //adding all 26 shatter purple bitmaps into the arraylist
            }
            this.initPurpleDiamonds = true;
        }

        if(!initYellowDiamonds){//preventing duplicates
            for(int y = 0; y < shatterYellow.length; y++){
                this.arrYellowBitmaps.add(shatterYellow[y]); //adding all 26 shatter yellow bitmaps into the arraylist
            }
            this.initYellowDiamonds = true;
        }

        if(!initBlueDiamonds){//preventing duplicates
            for(int b = 0; b < shatterBlue.length; b++){
                this.arrBlueBitmaps.add(shatterBlue[b]); //adding all 26 shatter blue bitmaps into the arraylist
            }
            this.initBlueDiamonds = true;
        }
    }

    private boolean shatterAnimationComplete(ArrayList<Bitmap> arrBitmaps){
        if(idCurrentBitmap == arrBitmaps.size()-1){
            return true;
        }
        else{
            return false;
        }
    }

    public void draw(Canvas canvas) {
        if (startShatter) { //collided correct color now animate shatter
            for (int i = 0; i < diamonds.size(); i++) {
                Diamond d = diamonds.get(i);
                if(!initShatterDiamonds) {
                    createDiamondShatterAnimator(i); //prevent duplicates
                    initShatterDiamonds = true;
                }
                if(this.bitmaps.get(i).sameAs(d.redDiamondBitmap)) { //if current index is red diamond then shatter animate red
                    canvas.drawBitmap(this.getBitmapAnimated(arrRedBitmaps), d.getXLocation(), d.getYLocation(), null);
                }
                else if(this.bitmaps.get(i).sameAs(d.greenDiamondBitmap)){//if current index is green diamond then shatter animate green
                    canvas.drawBitmap(this.getBitmapAnimated(arrGreenBitmaps), d.getXLocation(), d.getYLocation(), null);
                }
                else if(this.bitmaps.get(i).sameAs(d.purpleDiamondBitmap)){//if current index is purple diamond then shatter animate purple
                    canvas.drawBitmap(this.getBitmapAnimated(arrPurpleBitmaps), d.getXLocation(), d.getYLocation(), null);
                }
                else if(this.bitmaps.get(i).sameAs(d.yellowDiamondBitmap)){//if current index is yellow diamond then shatter animate yellow
                    canvas.drawBitmap(this.getBitmapAnimated(arrYellowBitmaps), d.getXLocation(), d.getYLocation(), null);
                }
                else if(this.bitmaps.get(i).sameAs(d.blueDiamondBitmap)){//if current index is blue diamond then shatter animate blue
                    canvas.drawBitmap(this.getBitmapAnimated(arrBlueBitmaps), d.getXLocation(), d.getYLocation(), null);
                }
            }
        } else { //when not doing shatter animation
            for (int i = 0; i < diamonds.size(); i++) {
                Diamond d = diamonds.get(i);
                this.setBitmap(d.getRandomDiamondColorBitmap()); //setting bitmap variable to a random color bitmap
                if (bitmaps.size() < numDiamonds) {
                    bitmaps.add(this.bitmap); // adding each new bitmap to linked list
                }
                canvas.drawBitmap(bitmaps.get(i), d.getXLocation(), d.getYLocation(), null);
            }
        }
    }
}



