void Phigros2_Chapter9_C9S6Unlock__StartC9S6(const MethodInfo *method)
{
  __int64 v1; // x1
  __int64 v2; // x1
  __int64 v3; // x1
  __int64 v4; // x1
  __int64 v5; // x1
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 v8; // x1
  __int64 v9; // x1
  struct GameInformation_o *main; // x19
  LevelStartInfo_o *levelStartInfo; // x20
  __int64 v12; // x0
  Il2CppObject *StringLiteral_11830; // x1
  __int64 StringLiteral; // x8
  System_Collections_Generic_List_object__o *levelMods; // x21
  float v16; // s0
  float v17; // s1
  float v18; // s2
  struct System_Object_array *items; // x8
  _QWORD *Method$System.Collections.Generic.List_string_.Add(); // x9
  __int64 size; // x10
  const MethodInfo *method_1; // x1
  System_Collections_IEnumerator_o *routine; // x1

  if ( (byte_446E5C3 & 1) == 0 ) /*0x1cf3390*/
  {
    sub_1B622A0(&LevelStartInfo_TypeInfo, v1); /*0x1cf339c*/
    sub_1B622A0(&Method_System_Collections_Generic_List_string__Add__, v2); /*0x1cf33a8*/
    sub_1B622A0(&Method_System_Collections_Generic_List_string___ctor__, v3); /*0x1cf33b4*/
    sub_1B622A0(&System_Collections_Generic_List_string__TypeInfo, v4); /*0x1cf33c0*/
    sub_1B622A0(&Phigros2_ChallengeMode_Scripts_SecretChallengeLifeRules_TypeInfo, v5); /*0x1cf33cc*/
    sub_1B622A0(&StringLiteral_11829, v6); /*0x1cf33d8*/
    sub_1B622A0(&StringLiteral_11830, v7); /*0x1cf33e4*/
    sub_1B622A0(&StringLiteral_11826, v8); /*0x1cf33f0*/
    sub_1B622A0(&StringLiteral_1, v9); /*0x1cf33fc*/
    byte_446E5C3 = 1; /*0x1cf3404*/
  }
  if ( !byte_446E372 ) /*0x1cf3418*/
  {
    sub_1B622A0(&GameInformation_TypeInfo, v1); /*0x1cf3424*/
    byte_446E372 = 1; /*0x1cf342c*/
  }
  main = GameInformation_TypeInfo->static_fields->_main; /*0x1cf3444*/
  levelStartInfo = (LevelStartInfo_o *)sub_1B624E8(LevelStartInfo_TypeInfo); /*0x1cf3450*/
  LevelStartInfo___ctor(levelStartInfo, 0); /*0x1cf3454*/
  if ( !levelStartInfo ) /*0x1cf3458*/
    goto LABEL_17; /*0x1cf3458*/
  StringLiteral = StringLiteral_1; /*0x1cf3470*/
  levelStartInfo->fields.songsId = (struct System_String_o *)StringLiteral_1; /*0x1cf3478*/
  levelStartInfo->fields.songsName = (struct System_String_o *)StringLiteral; /*0x1cf3478*/
  levelStartInfo->fields.composer = (struct System_String_o *)StringLiteral; /*0x1cf347c*/
  levelStartInfo->fields.charter = (struct System_String_o *)StringLiteral; /*0x1cf347c*/
  levelStartInfo->fields.illustrator = (struct System_String_o *)StringLiteral; /*0x1cf3480*/
  levelStartInfo->fields.songsLevel = (struct System_String_o *)StringLiteral; /*0x1cf3480*/
  levelStartInfo->fields.songsDifficulty = (struct System_String_o *)StringLiteral; /*0x1cf3484*/
  levelStartInfo->fields.musicAddressableKey = (struct System_String_o *)StringLiteral_11826; /*0x1cf3490*/
  levelStartInfo->fields.chartAddressableKey = (struct System_String_o *)StringLiteral_11829; /*0x1cf34a0*/
  levelStartInfo->fields.illustrationKey = (struct System_String_o *)StringLiteral; /*0x1cf34a0*/
  levelMods = (System_Collections_Generic_List_object__o *)sub_1B624E8(System_Collections_Generic_List_string__TypeInfo); /*0x1cf34b4*/
  System_Collections_Generic_List_object____ctor( /*0x1cf34b8*/
    levelMods,
    (const MethodInfo_2E4EE7C *)Method_System_Collections_Generic_List_string___ctor__);
  if ( !levelMods ) /*0x1cf34bc*/
    goto LABEL_17; /*0x1cf34bc*/
  StringLiteral_11830 = (Il2CppObject *)StringLiteral_11830; /*0x1cf34d4*/
  items = levelMods->fields._items; /*0x1cf34d8*/
  Method$System.Collections.Generic.List_string_.Add() = Method_System_Collections_Generic_List_string__Add__; /*0x1cf34dc*/
  ++levelMods->fields._version; /*0x1cf34e4*/
  if ( !items ) /*0x1cf34e8*/
    goto LABEL_17; /*0x1cf34e8*/
  size = levelMods->fields._size; /*0x1cf34ec*/
  if ( (unsigned int)size >= LODWORD(items->max_length) ) /*0x1cf34f8*/
  {
    System_Collections_Generic_List_object___AddWithResize( /*0x1cf3520*/
      levelMods,
      StringLiteral_11830,
      *(const MethodInfo_2E4F648 **)(*(_QWORD *)(Method$System.Collections.Generic.List_string_.Add()[4] + 192LL) + 112LL));
  }
  else
  {
    levelMods->fields._size = size + 1; /*0x1cf3504*/
    items->m_Items[size] = StringLiteral_11830; /*0x1cf3508*/
  }
  levelStartInfo->fields.levelMods = (struct System_Collections_Generic_List_string__o *)levelMods; /*0x1cf3524*/
  if ( !main ) /*0x1cf3528*/
LABEL_17:
    sub_1B624F8(v12, StringLiteral_11830); /*0x1cf35bc*/
  main->fields.levelStartInfo = levelStartInfo; /*0x1cf3534*/
  if ( !Phigros2_ChallengeMode_Scripts_SecretChallengeLifeRules_TypeInfo->_2.cctor_finished ) /*0x1cf353c*/
    j_il2cpp_runtime_class_init_0(v16, v17, v18); /*0x1cf3544*/
  Phigros2_ChallengeMode_Scripts_SecretChallengeLifeRules__ClearRuntimeState(main, 0); /*0x1cf3550*/
  main->fields.hiddenChallengeSongItem = 0; /*0x1cf355c*/
  GameInformation__set_ListenerVolume(1.0, 0); /*0x1cf3560*/
  if ( (byte_446E5C4 & 1) == 0 ) /*0x1cf3574*/
  {
    sub_1B622A0(&Phigros2_Chapter9_C9S6Unlock_TypeInfo, method_1); /*0x1cf3580*/
    byte_446E5C4 = 1; /*0x1cf3588*/
  }
  Phigros2_Chapter9_C9S6Unlock_TypeInfo->static_fields->pendingFadeInFromBlack = 1; /*0x1cf359c*/
  routine = Phigros2_Chapter9_C9S6Unlock__EnterLevel(main, method_1); /*0x1cf35a4*/
  UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)main, routine, 0); /*0x1cf35b8*/
}
