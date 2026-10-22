void Chapter8SelectMusicControl__Play(Chapter8SelectMusicControl_o *this, const MethodInfo *method)
{
  __int64 v3; // x1
  __int64 v4; // x1
  __int64 v5; // x1
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 v8; // x1
  __int64 songs; // x0
  struct SEPlayer_play_o *Play; // x8
  float v11; // s0
  float v12; // s1
  float v13; // s2
  struct LevelStartInfo_o *songs_1; // x20
  int32_t selectedSong; // w8
  __int64 v16; // x8
  _QWORD *Method$System.Collections.Generic.List_string_.Add(); // x9
  __int64 v18; // x10
  _QWORD *Method$System.Collections.Generic.List_string_.Add()_1; // x9
  struct GameInformation_o *main; // x8
  const MethodInfo *method_1; // x1
  System_Collections_IEnumerator_o *routine; // x1

  if ( (byte_446E842 & 1) == 0 )
  {
    sub_1B622A0(&Method_System_Collections_Generic_List_string__Add__, method);
    sub_1B622A0(&Method_System_Collections_Generic_List_SongSelectorItem__get_Item__, v3);
    sub_1B622A0(&SEPlayer_TypeInfo, v4);
    sub_1B622A0(&SaveManagement_TypeInfo, v5);
    sub_1B622A0(&"chapter8Passed", v6);
    sub_1B622A0(&"LevelMod/TheChariotLevelEffect", v7);
    sub_1B622A0(&"LevelMod/LuminescenceLevelEffect", v8);
    byte_446E842 = 1;
  }
  songs = (__int64)this->fields.songs;
  if ( !songs )
    goto LABEL_31;
  songs = (__int64)System_Collections_Generic_List_object___get_Item(
                     (System_Collections_Generic_List_object__o *)songs,
                     this->fields.selectedSong,
                     (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_SongSelectorItem__get_Item__);
  if ( !songs )
    goto LABEL_31;
  if ( *(_BYTE *)(songs + 24) && !this->fields.onPlay )
  {
    this->fields.onPlay = 1;
    Play = SEPlayer_TypeInfo->static_fields->Play;
    if ( !Play )
      goto LABEL_31;
    ((void (__fastcall *)(intptr_t, __int64, intptr_t))Play->fields.invoke_impl)(
      Play->fields.method_code,
      6,
      Play->fields.method);
    songs = (__int64)this->fields.songs;
    if ( !songs )
      goto LABEL_31;
    songs = (__int64)System_Collections_Generic_List_object___get_Item(
                       (System_Collections_Generic_List_object__o *)songs,
                       this->fields.selectedSong,
                       (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_SongSelectorItem__get_Item__);
    if ( !songs )
      goto LABEL_31;
    songs = (__int64)SongSelectorItem__GetLevelStartInfo((SongSelectorItem_o *)songs, this->fields.nowLevel, 0);
    if ( !songs )
      goto LABEL_31;
    songs_1 = (struct LevelStartInfo_o *)songs;
    *(_BYTE *)(songs + 96) = this->fields.enableChartMirror;
    if ( !SaveManagement_TypeInfo->_2.cctor_finished )
      j_il2cpp_runtime_class_init_0(v11, v12, v13);
    songs = SaveManagement__LoadBool((System_String_o *)"chapter8Passed", 0, 0);
    if ( (songs & 1) != 0 )
      goto LABEL_25;
    selectedSong = this->fields.selectedSong;
    if ( selectedSong == 1 )
    {
      songs = (__int64)songs_1->fields.levelMods;
      if ( !songs )
        goto LABEL_31;
      method = (const MethodInfo *)"LevelMod/TheChariotLevelEffect";
      v16 = *(_QWORD *)(songs + 16);
      Method$System.Collections.Generic.List_string_.Add() = Method_System_Collections_Generic_List_string__Add__;
      ++*(_DWORD *)(songs + 28);
      if ( !v16 )
        goto LABEL_31;
      v18 = *(int *)(songs + 24);
      if ( (unsigned int)v18 < *(_DWORD *)(v16 + 24) )
        goto LABEL_23;
      System_Collections_Generic_List_object___AddWithResize(
        (System_Collections_Generic_List_object__o *)songs,
        (Il2CppObject *)method,
        *(const MethodInfo_2E4F648 **)(*(_QWORD *)(Method$System.Collections.Generic.List_string_.Add()[4] + 192LL)
                                     + 112LL));
      selectedSong = this->fields.selectedSong;
    }
    if ( selectedSong != 2 )
      goto LABEL_25;
    songs = (__int64)songs_1->fields.levelMods;
    if ( !songs )
      goto LABEL_31;
    method = (const MethodInfo *)"LevelMod/LuminescenceLevelEffect";
    v16 = *(_QWORD *)(songs + 16);
    Method$System.Collections.Generic.List_string_.Add()_1 = Method_System_Collections_Generic_List_string__Add__;
    ++*(_DWORD *)(songs + 28);
    if ( !v16 )
      goto LABEL_31;
    v18 = *(int *)(songs + 24);
    if ( (unsigned int)v18 >= *(_DWORD *)(v16 + 24) )
    {
      System_Collections_Generic_List_object___AddWithResize(
        (System_Collections_Generic_List_object__o *)songs,
        (Il2CppObject *)method,
        *(const MethodInfo_2E4F648 **)(*(_QWORD *)(Method$System.Collections.Generic.List_string_.Add()_1[4] + 192LL)
                                     + 112LL));
LABEL_25:
      if ( !byte_446E372 )
      {
        songs = sub_1B622A0(&GameInformation_TypeInfo, method);
        byte_446E372 = 1;
      }
      main = GameInformation_TypeInfo->static_fields->_main;
      if ( main )
      {
        main->fields.levelStartInfo = songs_1;
        songs = (__int64)this->fields.illustratorText;
        if ( songs )
        {
          (*(void (__fastcall **)(__int64, struct System_String_o *, _QWORD))(*(_QWORD *)songs + 1512LL))(
            songs,
            songs_1->fields.illustrator,
            *(_QWORD *)(*(_QWORD *)songs + 1520LL));
          songs = (__int64)this->fields.charterText;
          if ( songs )
          {
            (*(void (__fastcall **)(__int64, struct System_String_o *, _QWORD))(*(_QWORD *)songs + 1512LL))(
              songs,
              songs_1->fields.charter,
              *(_QWORD *)(*(_QWORD *)songs + 1520LL));
            routine = Chapter8SelectMusicControl__StartAnimation(this, method_1);
            UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)this, routine, 0);
            return;
          }
        }
      }
LABEL_31:
      sub_1B624F8(songs, method);
    }
LABEL_23:
    *(_DWORD *)(songs + 24) = v18 + 1;
    *(_QWORD *)(v16 + 8 * v18 + 32) = method;
    goto LABEL_25;
  }
}
