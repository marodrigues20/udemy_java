package com.in28minutes.junit.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class SpringHelperTest {

  StringHelper helper;

  @Before
  public void setUp(){
     helper = new StringHelper();
  }

  @Test
  public void testTruncateAInFirst2Positions_AinFirst2Positions(){
    // AACD => CD ; ACD => CD ; CDEF => CDEF ; CDAA => CDAA
    assertEquals("CD", helper.truncateAInFirst2Positions("AACD"));
    assertEquals("CD", helper.truncateAInFirst2Positions("ACD"));
    //expected, actual
  }

  @Test
  public void testTruncateAInFirst2Positions2_AinFirstPosition(){
    // AACD => CD ; ACD => CD ; CDEF => CDEF ; CDAA => CDAA
    assertEquals("CD", helper.truncateAInFirst2Positions("ACD"));
    //expected, actual
  }


  // ABCD => false ; ABAB => true; AB => true, A => false
  @Test
  public void testAreFirstAndLastTwoCharactersTheSame_BasicNegativeScenario(){
    //assertEquals(false, helper.areFirstAndLastTwoCharactersTheSame("ABCD")); //OK
    assertFalse(helper.areFirstAndLastTwoCharactersTheSame("ABCD")); //THIS IS MORE READABLE
  }



}
