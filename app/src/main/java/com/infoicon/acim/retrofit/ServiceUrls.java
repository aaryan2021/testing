package com.infoicon.acim.retrofit;

/**
 * Created by Sumit Singh on 26/10/16.
 */

public class ServiceUrls {


  //public static final String BASE_URL="http://139.162.164.98/jcim/";
  public static final String BASE_URL="https://www.jcim.net/";

  //{"type":"chapters"}
  public static final String CHAPTER_URL="api/api.php";

  public static final String SAVE_BOOKMARK_="api/api.php";

  //{"type":"workbook"}
  public static final String WORKBOOK_LESSON="api/lesson.php";

  //{"type":"descriptionchapter","topic_id":"TxtChap-1-1","rtype":"text"}
  public static final String CHAPTER_DESC="api/descriptionchapter.php";

//{"type":"descriptionworkbook","topic_id":"WKBK-L1"}
  public static final String WORKBOOK_LESSON_DESC="api/descriptionworkbooklesson.php";

//{"type":"teachermanual"}
  public static final String TEACHER_MANUAL="api/teachermanual.php";

//{"type":"descriptionteacher","topic_id":"MNL-22"}
  public static final String TEACHER_MANUAL_DESC="api/descriptionteacher.php";

 // {"type":"textintro","key":"manual_teacher_intro"}

  /**
   * keys for diff sections
   * text_intro,workbook_part1_intro,workbook_part2_intro,workbook_epilogue,manual_teacher_intro
   * */
  public static final String INTRO_URL="api/introtext.php";

}
