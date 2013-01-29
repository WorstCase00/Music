package snippets;

import javax.sound.sampled.AudioFormat;

 public class Main {  
   public static void main(String[] args) throws Exception {  
     MicrophoneRecorder mr = new MicrophoneRecorder(new AudioFormat(8000, 8, 1, true, true));  
     mr.start();  
     Thread.sleep(2000);  
     mr.stop();  
     //save  
     Thread.sleep(3000);  
   }  
 }  



