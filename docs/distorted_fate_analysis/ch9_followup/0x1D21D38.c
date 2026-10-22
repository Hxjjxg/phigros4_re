void Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl___UnlockC9HiddenSongKeys_g__TryUnlock_30_0(
        Phigros2_SongSelector_Scripts_ChallengeSongItem_o *item,
        const MethodInfo *method)
{
  float v2; // s0
  float v3; // s1
  float v4; // s2
  __int64 v6; // x1
  __int64 v7; // x1
  struct SongSelectorItem_o *songInfo; // x8
  struct SongsItem_o *songInfo_1; // x8
  System_String_o *songsId; // x19
  Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_c *Phigros2.ChallengeMode.Scripts.ChallengeModeOverControl_TypeInf; // x0
  System_Collections_Generic_Dictionary_object__object__o *C9HiddenSongUnlockKeys; // x0
  float v13; // s0
  float v14; // s1
  float v15; // s2
  Il2CppObject *value_1; // x20
  bool Bool; // w20
  Il2CppObject *value; // [xsp+8h] [xbp-18h] BYREF

  if ( (byte_446E7A8 & 1) == 0 ) /*0x1d21d4c*/
  {
    sub_1B622A0(&Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_TypeInfo, method); /*0x1d21d58*/
    sub_1B622A0(&Method_System_Collections_Generic_Dictionary_string__string__TryGetValue__, v6); /*0x1d21d64*/
    sub_1B622A0(&SaveManagement_TypeInfo, v7); /*0x1d21d70*/
    byte_446E7A8 = 1; /*0x1d21d78*/
  }
  value = 0; /*0x1d21d7c*/
  if ( item ) /*0x1d21d80*/
  {
    songInfo = item->fields.songInfo; /*0x1d21d84*/
    if ( songInfo ) /*0x1d21d88*/
    {
      songInfo_1 = songInfo->fields.songInfo; /*0x1d21d8c*/
      if ( songInfo_1 ) /*0x1d21d90*/
      {
        songsId = songInfo_1->fields.songsId; /*0x1d21d9c*/
        Phigros2.ChallengeMode.Scripts.ChallengeModeOverControl_TypeInf = Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_TypeInfo; /*0x1d21da0*/
        if ( !Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_TypeInfo->_2.cctor_finished ) /*0x1d21da4*/
        {
          j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1d21dac*/
          Phigros2.ChallengeMode.Scripts.ChallengeModeOverControl_TypeInf = Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_TypeInfo; /*0x1d21db0*/
        }
        C9HiddenSongUnlockKeys = (System_Collections_Generic_Dictionary_object__object__o *)Phigros2.ChallengeMode.Scripts.ChallengeModeOverControl_TypeInf->static_fields->C9HiddenSongUnlockKeys; /*0x1d21db8*/
        if ( !C9HiddenSongUnlockKeys ) /*0x1d21dbc*/
          sub_1B624F8(0, method); /*0x1d21e40*/
        if ( System_Collections_Generic_Dictionary_object__object___TryGetValue( /*0x1d21dd4*/
               C9HiddenSongUnlockKeys,
               (Il2CppObject *)songsId,
               &value,
               (const MethodInfo_2A06704 *)Method_System_Collections_Generic_Dictionary_string__string__TryGetValue__) )
        {
          value_1 = value; /*0x1d21de4*/
          if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d21dec*/
            j_il2cpp_runtime_class_init_0(v13, v14, v15); /*0x1d21df4*/
          Bool = SaveManagement__LoadBool((System_String_o *)value_1, 0, 0); /*0x1d21e0c*/
          SaveManagement__SaveBool((System_String_o *)value, 1, 0); /*0x1d21e1c*/
          Phigros2_Chapter9_Chapter9UnlockNavigation__ApplyReturnToSelectMusicIfNeeded(songsId, Bool, 1, 0); /*0x1d21e30*/
        }
      }
    }
  }
}
