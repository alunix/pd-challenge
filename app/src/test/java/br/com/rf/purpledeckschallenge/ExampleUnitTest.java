package br.com.rf.purpledeckschallenge;

import org.junit.Test;

import br.com.rf.purpledeckschallenge.util.StringUtils;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    @Test
    public void removeAccent_isCorrect() throws Exception {
        assertEquals("sao paulo", StringUtils.removeAccents("são paulo"));
    }
}