package asct.ui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

  class ResultsDisplayPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JTextArea textArea;

    //-----------------------------------------------------------------
    ResultsDisplayPanel(){

       textArea = new JTextArea();
       textArea.setEditable(false);
       JScrollPane textScroll = new JScrollPane(textArea);
       this.setPreferredSize(new Dimension(300,300));
       this.setLayout(new BorderLayout());
       this.add(textScroll, BorderLayout.CENTER);

    }

    //-----------------------------------------------------------------
    public void loadText(String path){

      textArea.setText("");
      try{
        BufferedReader displayFile = new BufferedReader(
                        (new InputStreamReader
                          (new FileInputStream(new File(path)))));
        
        String line = null;
        while((line = displayFile.readLine()) != null)
          textArea.append(line + "\n");
        textArea.setCaretPosition(0);

      }
      catch(FileNotFoundException fnfe){
        fnfe.printStackTrace();
        System.exit(-1);
      }
      catch(IOException ioe){
        ioe.printStackTrace();
        System.exit(-1);
      }
    }
    

  }//class
