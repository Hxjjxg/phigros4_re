bool LuminescenceLevelEffect__UnlockRetribution_d__54__MoveNext(
        LuminescenceLevelEffect__UnlockRetribution_d__54_o *this,
        const MethodInfo *method)
{
  LuminescenceLevelEffect__UnlockRetribution_d__54_o *this_1; // x19
  __int64 v3; // x1
  __int64 v4; // x1
  __int64 v5; // x1
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 v8; // x1
  __int64 v9; // x1
  __int64 v10; // x1
  __int64 v11; // x1
  __int64 v12; // x1
  __int64 v13; // x1
  __int64 v14; // x1
  __int64 v15; // x1
  __int64 v16; // x1
  __int64 v17; // x1
  __int64 v18; // x1
  __int64 v19; // x1
  __int64 v20; // x1
  struct LuminescenceLevelEffect_o *_4__this; // x20
  System_Func_bool__o *predicate; // x21
  UnityEngine_WaitUntil_o *__2__current; // x20
  struct GlitchSettings_o *glitchSettings; // x8
  UnityEngine_WaitForSeconds_o *__2__current_1; // x20
  int n2; // w8
  struct GlitchSettings_o *glitchSettings_1; // x8
  UnityEngine_WaitForSeconds_o *__2__current_2; // x20
  struct GlitchSettings_o *glitchSettings_2; // x8
  UnityEngine_WaitForSeconds_o *__2__current_3; // x20
  struct GlitchSettings_o *glitchSettings_3; // x8
  UnityEngine_WaitForSeconds_o *__2__current_4; // x20
  struct Exposure_Saturate_ContrastSettings_o *escSettings; // x8
  struct GlitchSettings_o *glitchSettings_4; // x8
  UnityEngine_WaitForSeconds_o *__2__current_5; // x20
  struct GlitchSettings_o *glitchSettings_5; // x8
  UnityEngine_WaitForSeconds_o *__2__current_6; // x20
  struct Exposure_Saturate_ContrastSettings_o *escSettings_1; // x8
  System_Func_bool__o *predicate_1; // x21
  UnityEngine_WaitUntil_o *__2__current_7; // x20
  UnityEngine_WaitForSeconds_o *__2__current_8; // x20
  __int64 v48; // x21
  LevelRecord_o *this_2; // x19
  float v50; // s0
  float v51; // s1
  float v52; // s2
  __int64 v53; // x1
  float v54; // s0
  float v55; // s1
  float v56; // s2
  struct Phigros2_VisionReplay_VisionReplay_StaticFields *static_fields; // x8
  Phigros2_VisionReplay_VisionReplay_Key_c *Phigros2.VisionReplay.VisionReplay.Key_TypeInfo; // x0
  System_String_o *C8LuminescenceUnlocked_k__BackingField; // x19
  float v60; // s0
  float v61; // s1
  float v62; // s2
  System_String_o *key; // x19
  float v64; // s0
  float v65; // s1
  float v66; // s2
  int32_t this_3; // w19
  struct GameInformation_o *main; // x8
  struct SongBase_o *song; // x8
  System_Collections_Generic_IEnumerable_TSource__o *mainSongs; // x20
  LuminescenceLevelEffect___c_c *LuminescenceLevelEffect.__c_TypeInfo; // x0
  System_Func_object__bool__o *_9__54_2; // x21
  Il2CppObject *_; // x22
  float v74; // s0
  float v75; // s1
  float v76; // s2
  LuminescenceLevelEffect__UnlockRetribution_d__54_o *this_4; // x19
  __int64 v78; // x8
  _QWORD *Method$System.Collections.Generic.List_string_.Add(); // x9
  __int64 _2__current_low; // x10
  _QWORD *Method$System.Collections.Generic.List_string_.Add()_1; // x9
  __int64 v82; // x8
  __int64 v83; // x10
  struct GameInformation_o *_main; // x8
  UnityEngine_Object_o *background; // x19

  this_1 = this;
  if ( (byte_446E81B & 1) == 0 )
  {
    sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_C8Unlock_TypeInfo, method);
    sub_1B622A0(&Method_System_Linq_Enumerable_First_SongsItem___, v3);
    sub_1B622A0(&System_Func_bool__TypeInfo, v4);
    sub_1B622A0(&System_Func_SongsItem__bool__TypeInfo, v5);
    sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, v6);
    sub_1B622A0(&Method_System_Collections_Generic_List_string__Add__, v7);
    sub_1B622A0(&Method_LuminescenceLevelEffect__UnlockRetribution_b__54_0__, v8);
    sub_1B622A0(&Method_LuminescenceLevelEffect__UnlockRetribution_b__54_1__, v9);
    sub_1B622A0(&UnityEngine_Object_TypeInfo, v10);
    sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Process_TypeInfo, v11);
    sub_1B622A0(&SaveManagement_TypeInfo, v12);
    sub_1B622A0(&Method_LuminescenceLevelEffect___c__UnlockRetribution_b__54_2__, v13);
    sub_1B622A0(&LuminescenceLevelEffect___c_TypeInfo, v14);
    sub_1B622A0(&UnityEngine_WaitForSeconds_TypeInfo, v15);
    sub_1B622A0(&UnityEngine_WaitUntil_TypeInfo, v16);
    sub_1B622A0(&"LevelMod/RetributionEffectSecondPhase", v17);
    sub_1B622A0(&"LevelMod/HPDisplay", v18);
    sub_1B622A0(&"Level", v19);
    this = (LuminescenceLevelEffect__UnlockRetribution_d__54_o *)sub_1B622A0(&"C8LuminescenceUnlocked", v20);
    byte_446E81B = 1;
  }
  _4__this = this_1->fields.__4__this;
  switch ( this_1->fields.__1__state )
  {
    case 0:
      this_1->fields.__1__state = -1;
      predicate = (System_Func_bool__o *)sub_1B624E8(System_Func_bool__TypeInfo);
      System_Func_bool____ctor(
        predicate,
        (Il2CppObject *)_4__this,
        Method_LuminescenceLevelEffect__UnlockRetribution_b__54_0__,
        0);
      __2__current = (UnityEngine_WaitUntil_o *)sub_1B624E8(UnityEngine_WaitUntil_TypeInfo);
      UnityEngine_WaitUntil___ctor(__2__current, predicate, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current;
      this_1->fields.__1__state = 1;
      return 1;
    case 1:
      this_1->fields.__1__state = -1;
      if ( !_4__this )
        goto LABEL_90;
      glitchSettings = _4__this->fields.glitchSettings;
      if ( !glitchSettings )
        goto LABEL_90;
      glitchSettings->fields.glitchRange = 0.5;
      __2__current_1 = (UnityEngine_WaitForSeconds_o *)sub_1B624E8(UnityEngine_WaitForSeconds_TypeInfo);
      UnityEngine_WaitForSeconds___ctor(__2__current_1, 0.2, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_1;
      n2 = 2;
      goto LABEL_28;
    case 2:
      this_1->fields.__1__state = -1;
      if ( !_4__this )
        goto LABEL_90;
      glitchSettings_1 = _4__this->fields.glitchSettings;
      if ( !glitchSettings_1 )
        goto LABEL_90;
      glitchSettings_1->fields.glitchRange = 0.0;
      __2__current_2 = (UnityEngine_WaitForSeconds_o *)sub_1B624E8(UnityEngine_WaitForSeconds_TypeInfo);
      UnityEngine_WaitForSeconds___ctor(__2__current_2, 0.1, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_2;
      n2 = 3;
      goto LABEL_28;
    case 3:
      this_1->fields.__1__state = -1;
      if ( !_4__this )
        goto LABEL_90;
      glitchSettings_2 = _4__this->fields.glitchSettings;
      if ( !glitchSettings_2 )
        goto LABEL_90;
      glitchSettings_2->fields.glitchRange = 1.0;
      __2__current_3 = (UnityEngine_WaitForSeconds_o *)sub_1B624E8(UnityEngine_WaitForSeconds_TypeInfo);
      UnityEngine_WaitForSeconds___ctor(__2__current_3, 0.5, 0);
      n2 = 4;
      this_1->fields.__2__current = (Il2CppObject *)__2__current_3;
      goto LABEL_28;
    case 4:
      this_1->fields.__1__state = -1;
      if ( !_4__this )
        goto LABEL_90;
      glitchSettings_3 = _4__this->fields.glitchSettings;
      if ( !glitchSettings_3 )
        goto LABEL_90;
      glitchSettings_3->fields.glitchRange = 0.0;
      __2__current_4 = (UnityEngine_WaitForSeconds_o *)sub_1B624E8(UnityEngine_WaitForSeconds_TypeInfo);
      UnityEngine_WaitForSeconds___ctor(__2__current_4, 0.1, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_4;
      n2 = 5;
      goto LABEL_28;
    case 5:
      this_1->fields.__1__state = -1;
      if ( !_4__this )
        goto LABEL_90;
      escSettings = _4__this->fields.escSettings;
      if ( !escSettings )
        goto LABEL_90;
      *(_QWORD *)&escSettings->fields.saturation = 0xBF00000000000000LL;
      glitchSettings_4 = _4__this->fields.glitchSettings;
      if ( !glitchSettings_4 )
        goto LABEL_90;
      glitchSettings_4->fields.glitchRange = 3.0;
      __2__current_5 = (UnityEngine_WaitForSeconds_o *)sub_1B624E8(UnityEngine_WaitForSeconds_TypeInfo);
      UnityEngine_WaitForSeconds___ctor(__2__current_5, 0.5, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_5;
      n2 = 6;
      goto LABEL_28;
    case 6:
      this_1->fields.__1__state = -1;
      if ( !_4__this )
        goto LABEL_90;
      glitchSettings_5 = _4__this->fields.glitchSettings;
      if ( !glitchSettings_5 )
        goto LABEL_90;
      glitchSettings_5->fields.glitchRange = 0.5;
      __2__current_6 = (UnityEngine_WaitForSeconds_o *)sub_1B624E8(UnityEngine_WaitForSeconds_TypeInfo);
      UnityEngine_WaitForSeconds___ctor(__2__current_6, 0.1, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_6;
      n2 = 7;
      goto LABEL_28;
    case 7:
      this_1->fields.__1__state = -1;
      if ( !_4__this )
        goto LABEL_90;
      escSettings_1 = _4__this->fields.escSettings;
      if ( !escSettings_1 )
        goto LABEL_90;
      __asm { FMOV            V0.2S, #1.0 }
      *(_QWORD *)&escSettings_1->fields.saturation = _D0;
      predicate_1 = (System_Func_bool__o *)sub_1B624E8(System_Func_bool__TypeInfo);
      System_Func_bool____ctor(
        predicate_1,
        (Il2CppObject *)_4__this,
        Method_LuminescenceLevelEffect__UnlockRetribution_b__54_1__,
        0);
      __2__current_7 = (UnityEngine_WaitUntil_o *)sub_1B624E8(UnityEngine_WaitUntil_TypeInfo);
      UnityEngine_WaitUntil___ctor(__2__current_7, predicate_1, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_7;
      n2 = 8;
LABEL_28:
      this_1->fields.__1__state = n2;
      return 1;
    case 8:
      this_1->fields.__1__state = -1;
      __2__current_8 = (UnityEngine_WaitForSeconds_o *)sub_1B624E8(UnityEngine_WaitForSeconds_TypeInfo);
      UnityEngine_WaitForSeconds___ctor(__2__current_8, 0.5, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_8;
      n2 = 9;
      goto LABEL_28;
    case 9:
      this_1->fields.__1__state = -1;
      if ( !byte_446E372 )
      {
        sub_1B622A0(&GameInformation_TypeInfo, method);
        byte_446E372 = 1;
      }
      this = (LuminescenceLevelEffect__UnlockRetribution_d__54_o *)GameInformation_TypeInfo->static_fields->_main;
      if ( !this )
        goto LABEL_90;
      v48 = *((_QWORD *)this + 6);
      if ( !v48 )
        goto LABEL_90;
      this = (LuminescenceLevelEffect__UnlockRetribution_d__54_o *)GameInformation__GetSongRecord(
                                                                     (GameInformation_o *)this,
                                                                     *(System_String_o **)(v48 + 16),
                                                                     *(System_String_o **)(v48 + 56),
                                                                     0);
      if ( !_4__this )
        goto LABEL_90;
      this_2 = (LevelRecord_o *)this;
      this = (LuminescenceLevelEffect__UnlockRetribution_d__54_o *)_4__this->fields._scoreControl;
      if ( !this )
        goto LABEL_90;
      this = (LuminescenceLevelEffect__UnlockRetribution_d__54_o *)ScoreControl__GetLevelResultInfo(
                                                                     (ScoreControl_o *)this,
                                                                     0);
      if ( !this_2 )
        goto LABEL_90;
      LevelRecord__UpdateRecord(this_2, (LevelResultInfo_o *)this, 0);
      if ( !byte_446E372 )
      {
        sub_1B622A0(&GameInformation_TypeInfo, method);
        byte_446E372 = 1;
      }
      this = (LuminescenceLevelEffect__UnlockRetribution_d__54_o *)GameInformation_TypeInfo->static_fields->_main;
      if ( !this )
        goto LABEL_90;
      GameInformation__SetSongRecord(
        (GameInformation_o *)this,
        *(System_String_o **)(v48 + 16),
        *(System_String_o **)(v48 + 56),
        this_2,
        0);
      if ( !byte_446E372 )
      {
        sub_1B622A0(&GameInformation_TypeInfo, method);
        byte_446E372 = 1;
      }
      this = (LuminescenceLevelEffect__UnlockRetribution_d__54_o *)GameInformation_TypeInfo->static_fields->_main;
      if ( !this )
        goto LABEL_90;
      GameInformation__UpdateRankingScore((GameInformation_o *)this, 0);
      if ( !SaveManagement_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(v50, v51, v52);
      SaveManagement__SaveBool((System_String_o *)"C8LuminescenceUnlocked", 1, 0);
      if ( !byte_446E376 )
      {
        sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_TypeInfo, v53);
        byte_446E376 = 1;
      }
      static_fields = Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields;
      if ( static_fields->_IsOn_k__BackingField )
      {
        if ( !byte_446E377 )
        {
          sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_TypeInfo, v53);
          byte_446E377 = 1;
          static_fields = Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields;
        }
        if ( static_fields->_IsSimulateMode_k__BackingField )
        {
          if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished )
            j_il2cpp_runtime_class_init_0(v54, v55, v56);
          if ( !byte_446E86A )
          {
            sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, v53);
            byte_446E86A = 1;
          }
          Phigros2.VisionReplay.VisionReplay.Key_TypeInfo = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo;
          if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished )
          {
            j_il2cpp_runtime_class_init_0(v54, v55, v56);
            Phigros2.VisionReplay.VisionReplay.Key_TypeInfo = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo;
          }
          C8LuminescenceUnlocked_k__BackingField = Phigros2.VisionReplay.VisionReplay.Key_TypeInfo->static_fields->_C8LuminescenceUnlocked_k__BackingField;
          if ( !Phigros2_VisionReplay_VisionReplay_Process_TypeInfo->_2.cctor_finished )
            j_il2cpp_runtime_class_init_0(v54, v55, v56);
          Phigros2_VisionReplay_VisionReplay_Process__SetValue(C8LuminescenceUnlocked_k__BackingField, 1, 0);
          key = *(System_String_o **)(v48 + 16);
          if ( !Phigros2_VisionReplay_VisionReplay_C8Unlock_TypeInfo->_2.cctor_finished )
            j_il2cpp_runtime_class_init_0(v60, v61, v62);
          Phigros2_VisionReplay_VisionReplay_C8Unlock__SetValue(key, 1, 0);
        }
      }
      this = (LuminescenceLevelEffect__UnlockRetribution_d__54_o *)Assets_Phigros2_Utils_Tools__DifficultyToIndex(
                                                                     *(System_String_o **)(v48 + 56),
                                                                     0);
      this_3 = (int)this;
      if ( !byte_446E372 )
      {
        this = (LuminescenceLevelEffect__UnlockRetribution_d__54_o *)sub_1B622A0(&GameInformation_TypeInfo, method);
        byte_446E372 = 1;
      }
      main = GameInformation_TypeInfo->static_fields->_main;
      if ( !main )
        goto LABEL_90;
      song = main->fields.song;
      if ( !song )
        goto LABEL_90;
      mainSongs = (System_Collections_Generic_IEnumerable_TSource__o *)song->fields.mainSongs;
      LuminescenceLevelEffect.__c_TypeInfo = LuminescenceLevelEffect___c_TypeInfo;
      if ( !LuminescenceLevelEffect___c_TypeInfo->_2.cctor_finished )
      {
        j_il2cpp_runtime_class_init_0(v64, v65, v66);
        LuminescenceLevelEffect.__c_TypeInfo = LuminescenceLevelEffect___c_TypeInfo;
      }
      _9__54_2 = (System_Func_object__bool__o *)LuminescenceLevelEffect.__c_TypeInfo->static_fields->__9__54_2;
      if ( !_9__54_2 )
      {
        if ( !LuminescenceLevelEffect.__c_TypeInfo->_2.cctor_finished )
        {
          j_il2cpp_runtime_class_init_0(v64, v65, v66);
          LuminescenceLevelEffect.__c_TypeInfo = LuminescenceLevelEffect___c_TypeInfo;
        }
        _ = (Il2CppObject *)LuminescenceLevelEffect.__c_TypeInfo->static_fields->__9;
        _9__54_2 = (System_Func_object__bool__o *)sub_1B624E8(System_Func_SongsItem__bool__TypeInfo);
        System_Func_object__bool____ctor(
          _9__54_2,
          _,
          Method_LuminescenceLevelEffect___c__UnlockRetribution_b__54_2__,
          0);
        LuminescenceLevelEffect___c_TypeInfo->static_fields->__9__54_2 = (struct System_Func_SongsItem__bool__o *)_9__54_2;
      }
      this = (LuminescenceLevelEffect__UnlockRetribution_d__54_o *)System_Linq_Enumerable__First_object__32951596(
                                                                     mainSongs,
                                                                     (System_Func_TSource__bool__o *)_9__54_2,
                                                                     (const MethodInfo_1F6CD2C *)Method_System_Linq_Enumerable_First_SongsItem___);
      if ( !this )
        goto LABEL_90;
      this = (LuminescenceLevelEffect__UnlockRetribution_d__54_o *)SongsItem__GetLevelStartInfo(
                                                                     (SongsItem_o *)this,
                                                                     this_3,
                                                                     0);
      if ( !this )
        goto LABEL_90;
      this_4 = this;
      this = (LuminescenceLevelEffect__UnlockRetribution_d__54_o *)*((_QWORD *)this + 14);
      if ( !this )
        goto LABEL_90;
      method = (const MethodInfo *)"LevelMod/HPDisplay";
      v78 = *(_QWORD *)&this->fields.__1__state;
      Method$System.Collections.Generic.List_string_.Add() = Method_System_Collections_Generic_List_string__Add__;
      ++HIDWORD(this->fields.__2__current);
      if ( !v78 )
        goto LABEL_90;
      _2__current_low = SLODWORD(this->fields.__2__current);
      if ( (unsigned int)_2__current_low >= *(_DWORD *)(v78 + 24) )
      {
        System_Collections_Generic_List_object___AddWithResize(
          (System_Collections_Generic_List_object__o *)this,
          (Il2CppObject *)method,
          *(const MethodInfo_2E4F648 **)(*(_QWORD *)(Method$System.Collections.Generic.List_string_.Add()[4] + 192LL)
                                       + 112LL));
      }
      else
      {
        LODWORD(this->fields.__2__current) = _2__current_low + 1;
        *(_QWORD *)(v78 + 8 * _2__current_low + 32) = method;
      }
      this = (LuminescenceLevelEffect__UnlockRetribution_d__54_o *)this_4[2].fields.__4__this;
      if ( !this )
        goto LABEL_90;
      Method$System.Collections.Generic.List_string_.Add()_1 = Method_System_Collections_Generic_List_string__Add__;
      method = (const MethodInfo *)"LevelMod/RetributionEffectSecondPhase";
      v82 = *(_QWORD *)&this->fields.__1__state;
      ++HIDWORD(this->fields.__2__current);
      if ( !v82 )
        goto LABEL_90;
      v83 = SLODWORD(this->fields.__2__current);
      if ( (unsigned int)v83 >= *(_DWORD *)(v82 + 24) )
      {
        System_Collections_Generic_List_object___AddWithResize(
          (System_Collections_Generic_List_object__o *)this,
          (Il2CppObject *)method,
          *(const MethodInfo_2E4F648 **)(*(_QWORD *)(Method$System.Collections.Generic.List_string_.Add()_1[4] + 192LL)
                                       + 112LL));
      }
      else
      {
        LODWORD(this->fields.__2__current) = v83 + 1;
        *(_QWORD *)(v82 + 8 * v83 + 32) = method;
      }
      if ( !byte_446E372 )
      {
        this = (LuminescenceLevelEffect__UnlockRetribution_d__54_o *)sub_1B622A0(&GameInformation_TypeInfo, method);
        byte_446E372 = 1;
      }
      _main = GameInformation_TypeInfo->static_fields->_main;
      if ( !_main )
LABEL_90:
        sub_1B624F8(this, method);
      _main->fields.levelStartInfo = (struct LevelStartInfo_o *)this_4;
      background = (UnityEngine_Object_o *)_main->fields.background;
      if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(v74, v75, v76);
      UnityEngine_Object__Destroy_63300388(background, 0);
      BlackScreen__Show((System_String_o *)"Level", 2.0, 0);
      return 0;
    default:
      return 0;
  }
}
