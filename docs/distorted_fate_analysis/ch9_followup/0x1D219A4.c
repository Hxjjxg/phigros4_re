void Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl__UnlockC9HiddenSongKeys(const MethodInfo *method)
{
  __int64 v1; // x1
  float v2; // s0
  float v3; // s1
  float v4; // s2
  __int64 v5; // x1
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 v8; // x1
  __int64 Bool; // x0
  const MethodInfo *method_2; // x1
  float v11; // s0
  float v12; // s1
  float v13; // s2
  struct GameInformation_o *main; // x8
  struct System_Collections_Generic_List_ChallengeSongItem__o *challengeSongItems; // x9
  const MethodInfo *method_1; // x1
  Il2CppObject *Item; // x19
  float v18; // s0
  float v19; // s1
  float v20; // s2
  struct GameInformation_o *_main; // x8
  Phigros2_SongSelector_Scripts_ChallengeSongItem_o *hiddenChallengeSongItem; // x19

  if ( (byte_446E79F & 1) == 0 ) /*0x1d219bc*/
  {
    sub_1B622A0(&Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_TypeInfo, v1); /*0x1d219c8*/
    sub_1B622A0(&Method_System_Collections_Generic_List_ChallengeSongItem__get_Count__, v5); /*0x1d219d4*/
    sub_1B622A0(&Method_System_Collections_Generic_List_ChallengeSongItem__get_Item__, v6); /*0x1d219e0*/
    sub_1B622A0(&SaveManagement_TypeInfo, v7); /*0x1d219ec*/
    sub_1B622A0(&"Chapter9UnlockBegin", v8); /*0x1d219f8*/
    byte_446E79F = 1; /*0x1d21a00*/
  }
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d21a0c*/
    j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1d21a18*/
  Bool = SaveManagement__LoadBool((System_String_o *)"Chapter9UnlockBegin", 0, 0); /*0x1d21a28*/
  if ( (Bool & 1) != 0 ) /*0x1d21a2c*/
  {
    if ( !byte_446E372 ) /*0x1d21a38*/
    {
      Bool = sub_1B622A0(&GameInformation_TypeInfo, method_2); /*0x1d21a44*/
      byte_446E372 = 1; /*0x1d21a4c*/
    }
    main = GameInformation_TypeInfo->static_fields->_main; /*0x1d21a60*/
    if ( main ) /*0x1d21a64*/
    {
      if ( main->fields.challengeSongItems ) /*0x1d21a68*/
      {
        challengeSongItems = main->fields.challengeSongItems; /*0x1d21a70*/
        if ( !challengeSongItems ) /*0x1d21a74*/
          goto LABEL_21; /*0x1d21a74*/
        if ( challengeSongItems->fields._size >= 3 ) /*0x1d21a80*/
        {
          Bool = (__int64)main->fields.challengeSongItems; /*0x1d21a84*/
          if ( !Bool ) /*0x1d21a88*/
            goto LABEL_21; /*0x1d21a88*/
          Item = System_Collections_Generic_List_object___get_Item( /*0x1d21aa8*/
                   (System_Collections_Generic_List_object__o *)Bool,
                   2,
                   (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_ChallengeSongItem__get_Item__);
          if ( !Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_TypeInfo->_2.cctor_finished ) /*0x1d21ab0*/
            j_il2cpp_runtime_class_init_0(v18, v19, v20); /*0x1d21abc*/
          Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl___UnlockC9HiddenSongKeys_g__TryUnlock_30_0( /*0x1d21ac4*/
            (Phigros2_SongSelector_Scripts_ChallengeSongItem_o *)Item,
            method_1);
          if ( !byte_446E372 ) /*0x1d21acc*/
          {
            Bool = sub_1B622A0(&GameInformation_TypeInfo, method_2); /*0x1d21ad8*/
            byte_446E372 = 1; /*0x1d21ae0*/
          }
        }
      }
      _main = GameInformation_TypeInfo->static_fields->_main; /*0x1d21aec*/
      if ( _main ) /*0x1d21af0*/
      {
        hiddenChallengeSongItem = _main->fields.hiddenChallengeSongItem; /*0x1d21afc*/
        if ( !Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_TypeInfo->_2.cctor_finished ) /*0x1d21b04*/
          j_il2cpp_runtime_class_init_0(v11, v12, v13); /*0x1d21b0c*/
        Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl___UnlockC9HiddenSongKeys_g__TryUnlock_30_0( /*0x1d21b1c*/
          hiddenChallengeSongItem,
          method_2);
        return; /*0x1d21b1c*/
      }
    }
LABEL_21:
    sub_1B624F8(Bool, method_2); /*0x1d21b2c*/
  }
}
