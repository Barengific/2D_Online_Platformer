package musicgame;

import javax.sound.sampled.*;

/**
 * Plays audio from a buffer that is dynamically loaded.
 */
public class AudioPlayer implements LineListener {

    //Describes the format of the audio that is being played.
    AudioFormat audioFormat;

    //Used to play audio back by filling a buffer
    SourceDataLine soundLine;

    //Size of the buffer that is used to play audio
    int bufferSize;

    //Constructor
    AudioPlayer(AudioFormat format, int bufferSize) throws LineUnavailableException {
        //Store audio format and buffer size
        this.audioFormat = format;
        this.bufferSize = bufferSize;

        //Create sound line to play bytes
        soundLine = AudioSystem.getSourceDataLine(audioFormat);

        //Add listener to handle events in the sound line
        soundLine.addLineListener(this);

        //Display mixer information
       /* Mixer.Info[] mixerInfoArray = AudioSystem.getMixerInfo();
         for(int i=0; i<mixerInfoArray.length; ++i){
         System.out.println(mixerInfoArray[i].toString());
         }*/
    }

    //Adds data to the buffer that is used to play back the audio
    public void addData(byte[] byteArray) {
        int bytesWritten = soundLine.write(byteArray, 0, byteArray.length);
        if (true) {
            //System.out.println("ByteArray size: " + byteArray.length + "; Number of bytes written: " + bytesWritten + "; Number of bytes available: " + soundLine.available());
        }
    }

    //Opens and starts the sound line
    public void play() throws LineUnavailableException {
        //Start sound line
        soundLine.open(audioFormat, bufferSize);
        soundLine.start();
    }

    @Override
    public void update(LineEvent event) {
        //Output debugging information about the status of the sound line
        if (event.getType().equals(LineEvent.Type.CLOSE)) {
            if (true) {
                System.out.println("Audio player CLOSE event");
            }
        } else if (event.getType().equals(LineEvent.Type.OPEN)) {
            if (true) {
                System.out.println("Audio player OPEN event; buffer size: " + soundLine.getBufferSize());
            }
        } else if (event.getType().equals(LineEvent.Type.START)) {
            if (true) {
                System.out.println("Audio player START event");
            }
        } else if (event.getType().equals(LineEvent.Type.STOP)) {
            if (true) {
                System.out.println("Audio player STOP event");
            }
        } else {
            if (true) {
                System.err.println("Audio player event not recognized: " + event.toString());
            }
        }
    }

}
