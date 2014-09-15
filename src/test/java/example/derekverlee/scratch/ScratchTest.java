package example.derekverlee.scratch;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScratchTest {

    @Test
    public void metaTest() {
        assertThat(true, is(true));
    }

}