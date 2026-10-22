// local variable allocation has failed, the output may be wrong!
bool ChapterSelector___Start_g__IsSpecifiedSongReachScore_18_7(
        System_String_o *songId,
        int32_t score,
        const MethodInfo *method)
{
  __int64 v5; // x1
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 object; // x20
  GameInformation_o *main; // x0
  __int64 v10; // x1
  System_Collections_Generic_List_LevelRecord__o *SongAllRecord; // x19
  System_Func_object__bool__o *predicate; // x21

  if ( (byte_446E94D & 1) == 0 ) /*0x1d55afc*/
  {
    sub_1B622A0(&Method_System_Linq_Enumerable_All_LevelRecord___, *(_QWORD *)&score); /*0x1d55b08*/
    sub_1B622A0(&System_Func_LevelRecord__bool__TypeInfo, v5); /*0x1d55b14*/
    sub_1B622A0(&Method_ChapterSelector___c__DisplayClass18_3__Start_b__11__, v6); /*0x1d55b20*/
    sub_1B622A0(&ChapterSelector___c__DisplayClass18_3_TypeInfo, v7); /*0x1d55b2c*/
    byte_446E94D = 1; /*0x1d55b34*/
  }
  object = sub_1B624E8(ChapterSelector___c__DisplayClass18_3_TypeInfo); /*0x1d55b44*/
  System_Object___ctor((Il2CppObject *)object, 0); /*0x1d55b48*/
  if ( !object ) /*0x1d55b4c*/
    goto LABEL_8; /*0x1d55b4c*/
  *(_DWORD *)(object + 16) = score; /*0x1d55b50*/
  if ( !byte_446E372 ) /*0x1d55b5c*/
  {
    sub_1B622A0(&GameInformation_TypeInfo, v10); /*0x1d55b68*/
    byte_446E372 = 1; /*0x1d55b70*/
  }
  main = GameInformation_TypeInfo->static_fields->_main; /*0x1d55b84*/
  if ( !main ) /*0x1d55b88*/
LABEL_8:
    sub_1B624F8(main, v10); /*0x1d55bf0*/
  SongAllRecord = GameInformation__GetSongAllRecord(main, songId, 0); /*0x1d55bb4*/
  predicate = (System_Func_object__bool__o *)sub_1B624E8(System_Func_LevelRecord__bool__TypeInfo); /*0x1d55bcc*/
  System_Func_object__bool____ctor( /*0x1d55bd0*/
    predicate,
    (Il2CppObject *)object,
    Method_ChapterSelector___c__DisplayClass18_3__Start_b__11__,
    0);
  return System_Linq_Enumerable__All_object_(
           (System_Collections_Generic_IEnumerable_TSource__o *)SongAllRecord,
           (System_Func_TSource__bool__o *)predicate,
           (const MethodInfo_1F61CC4 *)Method_System_Linq_Enumerable_All_LevelRecord___);
}