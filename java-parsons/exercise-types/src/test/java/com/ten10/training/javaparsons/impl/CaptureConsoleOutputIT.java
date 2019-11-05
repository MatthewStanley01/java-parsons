package com.ten10.training.javaparsons.impl;

import com.ten10.training.javaparsons.impl.CaptureConsoleOutput.OutputStreamCombiner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CaptureConsoleOutputTest {

    private CaptureConsoleOutput cco = new CaptureConsoleOutput();
    private static final String LINE_ENDING = System.getProperty("line.separator");

    @Nested
    @DisplayName("start()")
    class Start {
        @Test
        @DisplayName("should begin recording")
        void start() throws IOException {
            //Act
            cco.start();
            System.out.println("I am a telly tubby developer");
            //Assert
            assertEquals("I am a telly tubby developer" + LINE_ENDING, cco.stop());
        }

        @Test
        @DisplayName("should not change System.out on a second invocation")
        void startShouldNotChangeSystemOutTwice() {
            //Act
            cco.start();
            PrintStream ps = System.out;
            cco.start();
            //Assert
            assertEquals(ps, System.out);
        }
    }

    @Nested
    @DisplayName("stop()")
    class Stop {
        @Test
        void stopRecordingWhenNotRecordingShouldThrow() {
            assertThrows(IllegalStateException.class, cco::stop);
        }


    }

    @Test
    void writeOutputStream() throws IOException {
        //Arrange
        OutputStream mockOutputStream = mock(OutputStream.class);
        List<OutputStream> listOutputStream = new ArrayList<>();
        listOutputStream.add(mockOutputStream);
        OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
        //Act
        outputStreamCombiner.write('b');
        //Assert
        verify(mockOutputStream).write('b');
    }

    @Nested
    @DisplayName("flush()")
    class Flush {

        @Test
        void flushOutputStream() throws IOException {
            //Arrange
            OutputStream mockOutputStream = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            //Act
            outputStreamCombiner.flush();
            //Assert
            verify(mockOutputStream).flush();
        }

        @Test
        void flushMultipleOutputStreams() throws IOException {
            //Arrange
            OutputStream mockOutputStream = mock(OutputStream.class);
            OutputStream mockOutputStream2 = mock(OutputStream.class);
            OutputStream mockOutputStream3 = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            listOutputStream.add(mockOutputStream2);
            listOutputStream.add(mockOutputStream3);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            //Act
            outputStreamCombiner.flush();
            //Assert
            verify(mockOutputStream).flush();
            verify(mockOutputStream2).flush();
            verify(mockOutputStream3).flush();
        }

        @Test
        void flushMultipleOutputStreamsWithIOException() throws IOException {
            //Arrange
            OutputStream mockOutputStream = mock(OutputStream.class);
            OutputStream mockOutputStream2 = mock(OutputStream.class);
            OutputStream mockOutputStream3 = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            listOutputStream.add(mockOutputStream2);
            listOutputStream.add(mockOutputStream3);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            doThrow(new IOException()).when(mockOutputStream2).flush();
            //Act
            assertThrows(IOException.class, outputStreamCombiner::flush);
            //Assert
            verify(mockOutputStream).flush();
            verify(mockOutputStream2).flush();
            verify(mockOutputStream3).flush();
        }

        @Test
        void flushMultipleOutputStreamsWithRuntimeException() throws IOException {
            //Arrange
            OutputStream mockOutputStream = mock(OutputStream.class);
            OutputStream mockOutputStream2 = mock(OutputStream.class);
            OutputStream mockOutputStream3 = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            listOutputStream.add(mockOutputStream2);
            listOutputStream.add(mockOutputStream3);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            doThrow(new RuntimeException()).when(mockOutputStream2).flush();
            //Act
            assertThrows(RuntimeException.class, outputStreamCombiner::flush);
            //Assert
            verify(mockOutputStream).flush();
            verify(mockOutputStream2).flush();
            verify(mockOutputStream3).flush();
        }

    }

    @Nested
    @DisplayName("close()")
    class Close {

        @Test
        void closeOutputStream() throws IOException {
            //Arrange
            OutputStream mockOutputStream = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            //Act
            outputStreamCombiner.close();
            //Assert
            verify(mockOutputStream).close();
        }

        @Test
        void closeMultipleOutputStreams() throws IOException {
            //Arrange
            OutputStream mockOutputStream = mock(OutputStream.class);
            OutputStream mockOutputStream2 = mock(OutputStream.class);
            OutputStream mockOutputStream3 = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            listOutputStream.add(mockOutputStream2);
            listOutputStream.add(mockOutputStream3);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            //Act
            outputStreamCombiner.close();
            //Assert
            verify(mockOutputStream).close();
            verify(mockOutputStream2).close();
            verify(mockOutputStream3).close();
        }

        @Test
        void closeMultipleOutputStreamsWithIOException() throws IOException {
            //Arrange
            OutputStream mockOutputStream = mock(OutputStream.class);
            OutputStream mockOutputStream2 = mock(OutputStream.class);
            OutputStream mockOutputStream3 = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            listOutputStream.add(mockOutputStream2);
            listOutputStream.add(mockOutputStream3);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            doThrow(new IOException()).when(mockOutputStream2).close();
            //Act
            assertThrows(IOException.class, outputStreamCombiner::close);
            //Aseert
            verify(mockOutputStream).close();
            verify(mockOutputStream2).close();
            verify(mockOutputStream3).close();
        }

        @Test
        void closeMultipleOutputStreamsWithRuntimeException() throws IOException {
            //Arrange
            OutputStream mockOutputStream = mock(OutputStream.class);
            OutputStream mockOutputStream2 = mock(OutputStream.class);
            OutputStream mockOutputStream3 = mock(OutputStream.class);
            List<OutputStream> listOutputStream = new ArrayList<>();
            listOutputStream.add(mockOutputStream);
            listOutputStream.add(mockOutputStream2);
            listOutputStream.add(mockOutputStream3);
            OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(listOutputStream);
            doThrow(new RuntimeException()).when(mockOutputStream2).close();
            //Act
            assertThrows(RuntimeException.class, outputStreamCombiner::close);
            //Assert
            verify(mockOutputStream).close();
            verify(mockOutputStream2).close();
            verify(mockOutputStream3).close();
        }
    }

}
