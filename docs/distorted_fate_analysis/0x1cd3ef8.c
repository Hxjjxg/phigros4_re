bool DFUnlockControl__Start_d__13__MoveNext(DFUnlockControl__Start_d__13_o *this, const MethodInfo *method)
{
  __int64 v2; // x2
  DFUnlockControl__Start_d__13_o *this_1; // x20
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
  struct DFUnlockControl_o *_4__this; // x19
  UnityEngine_WaitForSeconds_o *__2__current; // x19
  bool v22; // w21
  System_Func_bool__o *predicate; // x21
  UnityEngine_WaitUntil_o *__2__current_1; // x19
  const MethodInfo *method_1; // x1
  System_Collections_IEnumerator_o *routine; // x0
  System_Func_bool__o *predicate_1; // x22
  UnityEngine_WaitUntil_o *__2__current_2; // x19
  struct GameInformation_o *main; // x8
  struct LevelStartInfo_o *levelStartInfo; // x8
  System_String_o *songsLevel; // x20
  float v32; // s0
  float v33; // s1
  float v34; // s2
  int32_t n2; // w20
  struct GameInformation_o *_main; // x8
  struct SongBase_o *song; // x8
  System_Collections_Generic_IEnumerable_TSource__o *mainSongs; // x21
  DFUnlockControl___c_c *DFUnlockControl.__c_TypeInfo; // x0
  System_Func_object__bool__o *_9__13_2; // x22
  Il2CppObject *_; // x23
  float v42; // s0
  float v43; // s1
  float v44; // s2
  DFUnlockControl__Start_d__13_o *this_2; // x20
  __int64 v46; // x8
  _QWORD *Method$System.Collections.Generic.List_string_.Add(); // x9
  __int64 _2__current_low; // x10
  struct GameInformation_o *_main_1; // x8

  this_1 = this;
  if ( (byte_446E435 & 1) == 0 )
  {
    sub_1B622A0(&Method_DFUnlockControl__Start_b__13_0__, method);
    sub_1B622A0(&Method_DFUnlockControl__Start_b__13_1__, v4);
    sub_1B622A0(&Method_System_Linq_Enumerable_First_SongsItem___, v5);
    sub_1B622A0(&System_Func_bool__TypeInfo, v6);
    sub_1B622A0(&System_Func_SongsItem__bool__TypeInfo, v7);
    sub_1B622A0(&Method_System_Collections_Generic_List_string__Add__, v8);
    sub_1B622A0(&SaveManagement_TypeInfo, v9);
    sub_1B622A0(&Method_DFUnlockControl___c__Start_b__13_2__, v10);
    sub_1B622A0(&DFUnlockControl___c_TypeInfo, v11);
    sub_1B622A0(&UnityEngine_WaitForSeconds_TypeInfo, v12);
    sub_1B622A0(&UnityEngine_WaitUntil_TypeInfo, v13);
    sub_1B622A0(&"HD", v14);
    sub_1B622A0(&"AT", v15);
    sub_1B622A0(&"chartMirror", v16);
    sub_1B622A0(&"IN", v17);
    sub_1B622A0(&"EZ", v18);
    this = (DFUnlockControl__Start_d__13_o *)sub_1B622A0(&"LevelMod/DFLevelEffect", v19);
    byte_446E435 = 1;
  }
  _4__this = this_1->fields.__4__this;
  switch ( this_1->fields.__1__state )
  {
    case 0:
      this_1->fields.__1__state = -1;
      if ( !_4__this )
        goto LABEL_61;
      this = (DFUnlockControl__Start_d__13_o *)_4__this->fields.dfUnlockShaderVariantCollection;
      if ( !this )
        goto LABEL_61;
      UnityEngine_ShaderVariantCollection__WarmUp((UnityEngine_ShaderVariantCollection_o *)this, 0);
      this = (DFUnlockControl__Start_d__13_o *)_4__this->fields.player;
      if ( !this )
        goto LABEL_61;
      UnityEngine_Video_VideoPlayer__Prepare((UnityEngine_Video_VideoPlayer_o *)this, 0);
      this = (DFUnlockControl__Start_d__13_o *)_4__this->fields.touch2Start;
      if ( !this )
        goto LABEL_61;
      UnityEngine_Video_VideoPlayer__Prepare((UnityEngine_Video_VideoPlayer_o *)this, 0);
      __2__current = (UnityEngine_WaitForSeconds_o *)sub_1B624E8(UnityEngine_WaitForSeconds_TypeInfo);
      UnityEngine_WaitForSeconds___ctor(__2__current, 3.0, 0);
      v22 = 1;
      this_1->fields.__2__current = (Il2CppObject *)__2__current;
      this_1->fields.__1__state = 1;
      return v22;
    case 1:
      this_1->fields.__1__state = -1;
      predicate = (System_Func_bool__o *)sub_1B624E8(System_Func_bool__TypeInfo);
      System_Func_bool____ctor(predicate, (Il2CppObject *)_4__this, Method_DFUnlockControl__Start_b__13_0__, 0);
      __2__current_1 = (UnityEngine_WaitUntil_o *)sub_1B624E8(UnityEngine_WaitUntil_TypeInfo);
      UnityEngine_WaitUntil___ctor(__2__current_1, predicate, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_1;
      this_1->fields.__1__state = 2;
      return 1;
    case 2:
      this_1->fields.__1__state = -1;
      if ( !_4__this )
        goto LABEL_61;
      this = (DFUnlockControl__Start_d__13_o *)_4__this->fields.touch2Start;
      if ( !this )
        goto LABEL_61;
      UnityEngine_Video_VideoPlayer__Play((UnityEngine_Video_VideoPlayer_o *)this, 0);
      v22 = 1;
      _4__this->fields._isPlaying = 1;
      routine = DFUnlockControl__PostEffectWiggle(_4__this, method_1);
      UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)_4__this, routine, 0);
      predicate_1 = (System_Func_bool__o *)sub_1B624E8(System_Func_bool__TypeInfo);
      System_Func_bool____ctor(predicate_1, (Il2CppObject *)_4__this, Method_DFUnlockControl__Start_b__13_1__, 0);
      __2__current_2 = (UnityEngine_WaitUntil_o *)sub_1B624E8(UnityEngine_WaitUntil_TypeInfo);
      UnityEngine_WaitUntil___ctor(__2__current_2, predicate_1, 0);
      this_1->fields.__2__current = (Il2CppObject *)__2__current_2;
      this_1->fields.__1__state = 3;
      return v22;
    case 3:
      this_1->fields.__1__state = -1;
      if ( !_4__this )
        goto LABEL_61;
      this = (DFUnlockControl__Start_d__13_o *)_4__this->fields.touch2Start;
      if ( !this )
        goto LABEL_61;
      UnityEngine_Video_VideoPlayer__Pause((UnityEngine_Video_VideoPlayer_o *)this, 0);
      if ( !byte_446E372 )
      {
        this = (DFUnlockControl__Start_d__13_o *)sub_1B622A0(&GameInformation_TypeInfo, method);
        byte_446E372 = 1;
      }
      main = GameInformation_TypeInfo->static_fields->_main;
      if ( !main )
        goto LABEL_61;
      levelStartInfo = main->fields.levelStartInfo;
      if ( !levelStartInfo )
        goto LABEL_61;
      songsLevel = levelStartInfo->fields.songsLevel;
      this = (DFUnlockControl__Start_d__13_o *)System_String__op_Equality(
                                                 songsLevel,
                                                 (System_String_o *)"EZ",
                                                 0);
      if ( ((unsigned __int8)this & 1) != 0 )
      {
        n2 = 0;
      }
      else
      {
        this = (DFUnlockControl__Start_d__13_o *)System_String__op_Equality(
                                                   songsLevel,
                                                   (System_String_o *)"HD",
                                                   0);
        if ( ((unsigned __int8)this & 1) != 0 )
        {
          n2 = 1;
        }
        else
        {
          this = (DFUnlockControl__Start_d__13_o *)System_String__op_Equality(
                                                     songsLevel,
                                                     (System_String_o *)"IN",
                                                     0);
          if ( ((unsigned __int8)this & 1) != 0 )
          {
            n2 = 2;
          }
          else
          {
            this = (DFUnlockControl__Start_d__13_o *)System_String__op_Equality(
                                                       songsLevel,
                                                       (System_String_o *)"AT",
                                                       0);
            if ( ((unsigned __int8)this & 1) != 0 )
              n2 = 3;
            else
              n2 = 0;
          }
        }
      }
      if ( !byte_446E372 )
      {
        this = (DFUnlockControl__Start_d__13_o *)sub_1B622A0(&GameInformation_TypeInfo, method);
        byte_446E372 = 1;
      }
      _main = GameInformation_TypeInfo->static_fields->_main;
      if ( !_main )
        goto LABEL_61;
      song = _main->fields.song;
      if ( !song )
        goto LABEL_61;
      mainSongs = (System_Collections_Generic_IEnumerable_TSource__o *)song->fields.mainSongs;
      DFUnlockControl.__c_TypeInfo = DFUnlockControl___c_TypeInfo;
      if ( !DFUnlockControl___c_TypeInfo->_2.cctor_finished )
      {
        j_il2cpp_runtime_class_init_0(v32, v33, v34);
        DFUnlockControl.__c_TypeInfo = DFUnlockControl___c_TypeInfo;
      }
      _9__13_2 = (System_Func_object__bool__o *)DFUnlockControl.__c_TypeInfo->static_fields->__9__13_2;
      if ( !_9__13_2 )
      {
        if ( !DFUnlockControl.__c_TypeInfo->_2.cctor_finished )
        {
          j_il2cpp_runtime_class_init_0(v32, v33, v34);
          DFUnlockControl.__c_TypeInfo = DFUnlockControl___c_TypeInfo;
        }
        _ = (Il2CppObject *)DFUnlockControl.__c_TypeInfo->static_fields->__9;
        _9__13_2 = (System_Func_object__bool__o *)sub_1B624E8(System_Func_SongsItem__bool__TypeInfo);
        System_Func_object__bool____ctor(_9__13_2, _, Method_DFUnlockControl___c__Start_b__13_2__, 0);
        DFUnlockControl___c_TypeInfo->static_fields->__9__13_2 = (struct System_Func_SongsItem__bool__o *)_9__13_2;
      }
      this = (DFUnlockControl__Start_d__13_o *)System_Linq_Enumerable__First_object__32951596(
                                                 mainSongs,
                                                 (System_Func_TSource__bool__o *)_9__13_2,
                                                 (const MethodInfo_1F6CD2C *)Method_System_Linq_Enumerable_First_SongsItem___);
      if ( !this )
        goto LABEL_61;
      this = (DFUnlockControl__Start_d__13_o *)SongsItem__GetLevelStartInfo((SongsItem_o *)this, n2, 0);
      if ( !this )
        goto LABEL_61;
      this_2 = this;
      this = (DFUnlockControl__Start_d__13_o *)*((_QWORD *)this + 14);
      if ( !this )
        goto LABEL_61;
      method = (const MethodInfo *)"LevelMod/DFLevelEffect";
      v46 = *(_QWORD *)&this->fields.__1__state;
      Method$System.Collections.Generic.List_string_.Add() = Method_System_Collections_Generic_List_string__Add__;
      ++HIDWORD(this->fields.__2__current);
      if ( !v46 )
        goto LABEL_61;
      _2__current_low = SLODWORD(this->fields.__2__current);
      if ( (unsigned int)_2__current_low >= *(_DWORD *)(v46 + 24) )
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
        *(_QWORD *)(v46 + 8 * _2__current_low + 32) = method;
      }
      if ( !byte_446E376 )
      {
        this = (DFUnlockControl__Start_d__13_o *)sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_TypeInfo, method);
        byte_446E376 = 1;
      }
      if ( Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields->_IsOn_k__BackingField )
      {
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v42, v43, v44);
        this = (DFUnlockControl__Start_d__13_o *)SaveManagement__LoadBool((System_String_o *)"chartMirror", 0, 0);
        LOBYTE(this_2[2].fields.__1__state) = (unsigned __int8)this & 1;
      }
      if ( !byte_446E372 )
      {
        this = (DFUnlockControl__Start_d__13_o *)sub_1B622A0(&GameInformation_TypeInfo, method);
        byte_446E372 = 1;
      }
      _main_1 = GameInformation_TypeInfo->static_fields->_main;
      if ( !_main_1 )
        goto LABEL_61;
      _main_1->fields.levelStartInfo = (struct LevelStartInfo_o *)this_2;
      this = (DFUnlockControl__Start_d__13_o *)_4__this->fields.charterText;
      if ( !this
        || (((void (__fastcall *)(DFUnlockControl__Start_d__13_o *, DFUnlockControl__Start_d__13_c *, void *))this->klass[3]._1.properties)(
              this,
              this_2[1].klass,
              this->klass[3]._1.methods),
            (this = (DFUnlockControl__Start_d__13_o *)_4__this->fields.illustratorText) == 0)
        || (((void (__fastcall *)(DFUnlockControl__Start_d__13_o *, void *, void *))this->klass[3]._1.properties)(
              this,
              this_2[1].monitor,
              this->klass[3]._1.methods),
            (this = (DFUnlockControl__Start_d__13_o *)_4__this->fields.difficultyText) == 0)
        || (((void (__fastcall *)(DFUnlockControl__Start_d__13_o *, Il2CppObject *, void *))this->klass[3]._1.properties)(
              this,
              this_2[1].fields.__2__current,
              this->klass[3]._1.methods),
            (this = (DFUnlockControl__Start_d__13_o *)_4__this->fields.levelNameText) == 0) )
      {
LABEL_61:
        sub_1B624F8(this, method, v2);
      }
      ((void (__fastcall *)(DFUnlockControl__Start_d__13_o *, _QWORD, void *))this->klass[3]._1.properties)(
        this,
        *(_QWORD *)&this_2[1].fields.__1__state,
        this->klass[3]._1.methods);
      return 0;
    default:
      return 0;
  }
}
