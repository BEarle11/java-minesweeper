
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.util.Random;
import javax.swing.JLabel;
import java.util.ArrayList;
import javax.swing.Timer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author brianearle
 */
public class MineSweeperFrame extends javax.swing.JFrame implements ActionListener{
    int size=12;
    int highscore=600;
    int num2Str;
    int timerCount=0;
    boolean flagButtonToggle=false;
    Random random = new Random();
    JButton[][]butArray = new JButton[16][16];
    JLabel[][]textArray= new JLabel[16][16];
    int[][]tiles;
    int[][]adjBoolean;
    Color oneColor = new Color(173,200 , 250);
    Color twoColor = new Color(144,173 , 250);
    Color threeColor = new Color(75,134 , 250);
    Color fourColor = new Color(53,119 , 250);
    Color fiveColor = new Color(14,93 , 250);
    Color zeroGreen= new Color(60,128 , 43);
    Timer t1 = new Timer(800,this);
    
    /**
     * Creates new form MineSweeperFrame
     */
    public MineSweeperFrame() {
        initComponents();
        createMap(size);
        initialize();
        sizeSlider.setValue(12);
    }
    private void createMap(int size){
        // 2d array of each tiles value
        tiles = new int[size][size];
        // 2d array of tiles that have been checked for adjacent zeros
        adjBoolean = new int[size][size];
        /// sets all tiles to 0 
        for (int x=0;x<size;x++)
            for (int y=0;y<size;y++){
                tiles[x][y]=0;
                adjBoolean[x][y]=1;
            }
        /// randomly plants the bombs as -1 
        for (int i=0;i<size*1.5;i++){
                int ranCol=random.nextInt(1,size-1);
                int ranRow=random.nextInt(1,size-1);
                if ((tiles[ranCol][ranRow])!=-1){
                    tiles[ranCol][ranRow]=-1;
            }
                else{
                    i-=1;
                }
            }
        /// checks for adjacent bombs  and adding values
        for (int x=0;x<size;x++)
            for (int y=0;y<size;y++){
                if (tiles[x][y]==-1){
                    for (int j=-1;j<2;j++)
                        for (int k=-1;k<2;k++){
                            if (!(j==0 && k==0)){
                                if (tiles[x+j][y+k]!=-1){
                                    tiles[x+j][y+k]+=1;
                                }
                            }
                            
                        }
                }
            }
    }
    private void initialize(){
        // places buttons
        //sizeSlider.setValue(12);
        butPAN.setLayout(new GridLayout(size,size));
        for (int x=0;x<size;x++)
            for (int y=0;y<size;y++){
                butArray[x][y]= new JButton();
                butArray[x][y].addActionListener(this);
                butArray[x][y].setSize(50,50);
                butPAN.add(butArray[x][y]);            
            }
        validate();    
    }
    
    public void flagAction(){
        // checking if flag button is on
        
        if (flagButtonToggle==false){
                    flagButtonToggle=true;
                }
            else{
                    flagButtonToggle=false;
                }
    }
    private void gameOver(){
        t1.stop();
        jLabel1.setText("Good Job!");
        for (int x=0;x<size;x++)
            for(int y=0;y<size;y++){
                if(tiles[x][y]==-2||tiles[x][y]==-1){
                    butArray[x][y].setBackground(zeroGreen);
                }
                else{
                    butArray[x][y].setBackground(Color.black);
                    butArray[x][y].setText("");
                }
            }
        if (timerCount<highscore){
            bestTimeLabel.setText("Best Time: " + timerCount);
        }
    }
    @Override
    public void actionPerformed(ActionEvent ae){
        t1.start();
        boolean firstCheck=false;
        boolean adjZero=false;
        ArrayList<Integer> adjList= new ArrayList<>();
        int tileX=0;
        int tileY=0;
        //checks for timer
        if (ae.getSource()==t1){
            timerCount++;
            System.out.println(timerCount);
            timerLabel.setText("Timer: " + timerCount);
            
        }
        // identifies tile clicked and puts number on tile  
        else{ for (int x=0;x<size;x++)
            for(int y=0;y<size;y++){
                if(ae.getSource()==butArray[x][y]){
                    if (flagButtonToggle==true){
                        butArray[x][y].setBackground(Color.black);
                        // checking to see if game was won 
                        boolean allBombsFlagged=true;
                        if (tiles[x][y]==-1){
                            tiles[x][y]=-2;
                            for (int j=0;j<size;j++)
                                for(int k=0;k<size;k++){
                                    if (tiles[j][k]==-1){
                                        allBombsFlagged=false;
                                    }
                                }
                            if(allBombsFlagged==true){
                                gameOver();
                            }
                    }
                }
                    if (flagButtonToggle==false){
                    num2Str=tiles[x][y];
                    butArray[x][y].setText(Integer.toString(num2Str));
                    tileX=x;
                    tileY=y;
                    // sets colors
                    if(tiles[x][y]==1){butArray[x][y].setBackground(oneColor);}
                    if(tiles[x][y]==2){butArray[x][y].setBackground(twoColor);}
                    if(tiles[x][y]==3){butArray[x][y].setBackground(threeColor);}
                    if(tiles[x][y]==4){butArray[x][y].setBackground(fourColor);}
                    if(tiles[x][y]>=5){butArray[x][y].setBackground(fiveColor);}
                    if(tiles[x][y]==0){butArray[x][y].setBackground(zeroGreen);}
                    validate();
                    }
                }
                
    }
        
                // makes bombs red and ends game 
                if((tiles[tileX][tileY]==-1||tiles[tileX][tileY]==-2)&&(flagButtonToggle==false)){
                    t1.stop();
                    butArray[tileX][tileY].setBackground(Color.red);
                    for (int x=0;x<size;x++)
                    for(int y=0;y<size;y++){
                        num2Str=tiles[x][y];
                        if(tiles[x][y]!=0){butArray[x][y].setText(Integer.toString(num2Str));}
                        if(tiles[x][y]==-1){
                            butArray[x][y].setBackground(Color.red);
                        }
                        if(tiles[x][y]==1){butArray[x][y].setBackground(oneColor);}
                        if(tiles[x][y]==2){butArray[x][y].setBackground(twoColor);}
                        if(tiles[x][y]==3){butArray[x][y].setBackground(threeColor);}
                        if(tiles[x][y]==4){butArray[x][y].setBackground(fourColor);}
                        if(tiles[x][y]>=5){butArray[x][y].setBackground(fiveColor);}
                        if(tiles[x][y]==0){butArray[x][y].setBackground(zeroGreen);}
                        jLabel1.setText("Try Again");
                        
                        
                    }
                }
                
                // checks for adjacent 0s after a tile is clicked
                if ((tiles[tileX][tileY]==0)&&(flagButtonToggle==false)){
                    adjList.add(tileX);
                    adjList.add(tileY);
                    adjZero=true;
                    firstCheck=true;
                    while(adjZero==true){
                        if(firstCheck==false){
                            tileX=adjList.get(0);
                            tileY=adjList.get(1);
                        }
                        firstCheck=false;
                        for (int j=-1;j<2;j++)
                        for (int k=-1;k<2;k++){
                            if (!(j==0 && k==0)){
                                if(!((tileX==0 && j==-1)||(tileX==size-1 && j==1)||(tileY==0 && k==-1)||(tileY==size-1 && k==1))){
                                    int adjCheck=tiles[tileX+j][tileY+k];
                                    if((adjCheck!=0)){
                                        butArray[tileX+j][tileY+k].setText(Integer.toString(adjCheck));
                                        if(tiles[tileX+j][tileY+k]==1){butArray[tileX+j][tileY+k].setBackground(oneColor);}
                                        if(tiles[tileX+j][tileY+k]==2){butArray[tileX+j][tileY+k].setBackground(twoColor);}
                                        if(tiles[tileX+j][tileY+k]==3){butArray[tileX+j][tileY+k].setBackground(threeColor);}
                                        if(tiles[tileX+j][tileY+k]==4){butArray[tileX+j][tileY+k].setBackground(fourColor);}
                                        if(tiles[tileX+j][tileY+k]>=5){butArray[tileX+j][tileY+k].setBackground(fiveColor);}
                                    }
                                    if ((adjCheck==0) && (adjBoolean[tileX+j][tileY+k]==1)){
                                        adjBoolean[tileX+j][tileY+k]=0;
                                        num2Str=adjCheck;
                                        //butArray[tileX+j][tileY+k].setText(Integer.toString(num2Str));
                                        validate();
                                        butArray[tileX+j][tileY+k].setBackground(zeroGreen);
                                        adjList.add(tileX+j);
                                        adjList.add(tileY+k);
                                    }
                                }
                         
                            }
                            
                        }
                            adjList.remove(1);
                            adjList.remove(0);
                            if(adjList.isEmpty()){adjZero=false;}
                    }
            }
                
    }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        butPAN = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        flagButton = new javax.swing.JToggleButton();
        resetButton = new javax.swing.JButton();
        sizeSlider = new javax.swing.JSlider();
        sizeLabel = new javax.swing.JLabel();
        timerLabel = new javax.swing.JLabel();
        bestTimeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 700));
        getContentPane().setLayout(null);

        javax.swing.GroupLayout butPANLayout = new javax.swing.GroupLayout(butPAN);
        butPAN.setLayout(butPANLayout);
        butPANLayout.setHorizontalGroup(
            butPANLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
        );
        butPANLayout.setVerticalGroup(
            butPANLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );

        getContentPane().add(butPAN);
        butPAN.setBounds(5, 89, 690, 350);

        jLabel1.setFont(new java.awt.Font("Oriya MN", 3, 24)); // NOI18N
        jLabel1.setText("MineSweeper");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(250, 30, 240, 40);

        flagButton.setText("Flag Button ");
        flagButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flagButtonActionPerformed(evt);
            }
        });
        getContentPane().add(flagButton);
        flagButton.setBounds(530, 30, 150, 50);

        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });
        getContentPane().add(resetButton);
        resetButton.setBounds(20, 30, 120, 50);
        getContentPane().add(sizeSlider);
        sizeSlider.setBounds(70, 480, 200, 20);

        sizeLabel.setFont(new java.awt.Font("Oriya MN", 0, 18)); // NOI18N
        sizeLabel.setText("Size: 12");
        getContentPane().add(sizeLabel);
        sizeLabel.setBounds(100, 450, 110, 22);

        timerLabel.setFont(new java.awt.Font("Oriya MN", 0, 20)); // NOI18N
        timerLabel.setText("Timer:    ");
        getContentPane().add(timerLabel);
        timerLabel.setBounds(350, 470, 180, 17);

        bestTimeLabel.setFont(new java.awt.Font("Oriya MN", 0, 20)); // NOI18N
        bestTimeLabel.setText("Best Time: ");
        getContentPane().add(bestTimeLabel);
        bestTimeLabel.setBounds(490, 470, 210, 25);

        getAccessibleContext().setAccessibleName("gamePAN2");
        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void flagButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flagButtonActionPerformed
        flagAction();
    }//GEN-LAST:event_flagButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        t1.stop();  
        timerCount=0;
        jLabel1.setText("MineSweeper");
        for (int x=0;x<size;x++)
            for (int y=0;y<size;y++){
                butPAN.remove(butArray[x][y]);
            }
        sizeSlider.setMinimum(8);
        sizeSlider.setMaximum(16);
        sizeSlider.setMajorTickSpacing(2);
        sizeSlider.setMinorTickSpacing(1);
        size=sizeSlider.getValue();
        sizeLabel.setText("Size: " + size);
        createMap(size);
        initialize();   
    }//GEN-LAST:event_resetButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MineSweeperFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MineSweeperFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MineSweeperFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MineSweeperFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MineSweeperFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bestTimeLabel;
    private javax.swing.JPanel butPAN;
    private javax.swing.JToggleButton flagButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton resetButton;
    private javax.swing.JLabel sizeLabel;
    private javax.swing.JSlider sizeSlider;
    private javax.swing.JLabel timerLabel;
    // End of variables declaration//GEN-END:variables
}
