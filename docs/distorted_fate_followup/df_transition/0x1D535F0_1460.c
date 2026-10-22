          ChapterSelector.__c_TypeInfo = ChapterSelector___c_TypeInfo; /*0x1d54c64*/
          chapters = (System_Collections_Generic_IEnumerable_TSource__o *)_main->fields.chapters; /*0x1d54c68*/
          if ( !ChapterSelector___c_TypeInfo->_2.cctor_finished ) /*0x1d54c6c*/
          {
            j_il2cpp_runtime_class_init_0(v209, v210, v211); /*0x1d54c74*/
            ChapterSelector.__c_TypeInfo = ChapterSelector___c_TypeInfo; /*0x1d54c78*/
          }
          _9__18_3 = (System_Func_object__bool__o *)ChapterSelector.__c_TypeInfo->static_fields->__9__18_3; /*0x1d54c80*/
          if ( !_9__18_3 ) /*0x1d54c84*/
          {
            if ( !ChapterSelector.__c_TypeInfo->_2.cctor_finished ) /*0x1d54c88*/
            {
              j_il2cpp_runtime_class_init_0(v209, v210, v211); /*0x1d54c90*/
              ChapterSelector.__c_TypeInfo = ChapterSelector___c_TypeInfo; /*0x1d54c94*/
            }
            _ = (Il2CppObject *)ChapterSelector.__c_TypeInfo->static_fields->__9; /*0x1d54c9c*/
            _9__18_3 = (System_Func_object__bool__o *)sub_1B624E8(System_Func_Chapter__bool__TypeInfo); /*0x1d54cc0*/
            System_Func_object__bool____ctor(_9__18_3, _, Method_ChapterSelector___c__Start_b__18_3__, 0); /*0x1d54cc8*/
            ChapterSelector___c_TypeInfo->static_fields->__9__18_3 = (struct System_Func_Chapter__bool__o *)_9__18_3; /*0x1d54cd4*/
          }
          chapterPanel = (__int64)System_Linq_Enumerable__First_object__32951596( /*0x1d54cec*/
                                    chapters,
                                    (System_Func_TSource__bool__o *)_9__18_3,
                                    (const MethodInfo_1F6CD2C *)Method_System_Linq_Enumerable_First_Chapter___);
          if ( !chapterPanel ) /*0x1d54cf0*/
            goto LABEL_410; /*0x1d54cf0*/
          v257 = *(_QWORD *)(chapterPanel + 24); /*0x1d54cf4*/
          if ( !v257 ) /*0x1d54cf8*/
            goto LABEL_410; /*0x1d54cf8*/
          ChapterSelector.__c_TypeInfo_1 = ChapterSelector___c_TypeInfo; /*0x1d54cfc*/
          source = *(System_Collections_Generic_IEnumerable_TSource__o **)(v257 + 48); /*0x1d54d00*/
          if ( !ChapterSelector___c_TypeInfo->_2.cctor_finished ) /*0x1d54d04*/
          {
            j_il2cpp_runtime_class_init_0(v254, v255, v256); /*0x1d54d0c*/
            ChapterSelector.__c_TypeInfo_1 = ChapterSelector___c_TypeInfo; /*0x1d54d10*/
          }
          _9__18_4 = (System_Func_object__object__o *)ChapterSelector.__c_TypeInfo_1->static_fields->__9__18_4; /*0x1d54d18*/
          if ( !_9__18_4 ) /*0x1d54d1c*/
          {
            if ( !ChapterSelector.__c_TypeInfo_1->_2.cctor_finished ) /*0x1d54d20*/
            {
              j_il2cpp_runtime_class_init_0(v254, v255, v256); /*0x1d54d28*/
              ChapterSelector.__c_TypeInfo_1 = ChapterSelector___c_TypeInfo; /*0x1d54d2c*/
            }
            __1 = (Il2CppObject *)ChapterSelector.__c_TypeInfo_1->static_fields->__9; /*0x1d54d34*/
            _9__18_4 = (System_Func_object__object__o *)sub_1B624E8(System_Func_ChapterSongItem__string__TypeInfo); /*0x1d54d58*/
            System_Func_object__object____ctor(_9__18_4, __1, Method_ChapterSelector___c__Start_b__18_4__, 0); /*0x1d54d60*/
            ChapterSelector___c_TypeInfo->static_fields->__9__18_4 = (struct System_Func_ChapterSongItem__string__o *)_9__18_4; /*0x1d54d6c*/
          }
          source_1 = (System_Collections_Generic_IEnumerable_TSource__o *)System_Linq_Enumerable__Select_object__object_( /*0x1d54d84*/
                                                                            source,
                                                                            (System_Func_TSource__TResult__o *)_9__18_4,
                                                                            (const MethodInfo_1F747E0 *)Method_System_Linq_Enumerable_Select_ChapterSongItem__string___);
          chapterPanel = (__int64)System_Linq_Enumerable__ToArray_object_( /*0x1d54d94*/
                                    source_1,
                                    (const MethodInfo_1F7ABAC *)Method_System_Linq_Enumerable_ToArray_string___);
          chapterPanel_5 = chapterPanel; /*0x1d54da0*/
          if ( !byte_446E376 ) /*0x1d54da4*/
          {
            chapterPanel = sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_TypeInfo, method); /*0x1d54db0*/
            byte_446E376 = 1; /*0x1d54dbc*/
          }
          static_fields_2 = Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields; /*0x1d54dcc*/
          if ( !static_fields_2->_IsOn_k__BackingField ) /*0x1d54dd0*/
            goto LABEL_395; /*0x1d54dd0*/
          if ( !byte_446E377 ) /*0x1d54de0*/
          {
            chapterPanel = sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_TypeInfo, method); /*0x1d54df0*/
            byte_446E377 = 1; /*0x1d54e00*/
            static_fields_2 = Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields; /*0x1d54e04*/
          }
          if ( static_fields_2->_IsSimulateMode_k__BackingField ) /*0x1d54e08*/
          {
            if ( !byte_446E372 ) /*0x1d54e18*/
            {
              chapterPanel = sub_1B622A0(&GameInformation_TypeInfo, method); /*0x1d54e24*/
              byte_446E372 = 1; /*0x1d54e30*/
            }
            _main_1 = GameInformation_TypeInfo->static_fields->_main; /*0x1d54e44*/
            if ( !_main_1 ) /*0x1d54e48*/
              goto LABEL_410; /*0x1d54e48*/
            ChapterSelector.__c_TypeInfo_2 = ChapterSelector___c_TypeInfo; /*0x1d54e4c*/
            chapters_1 = (System_Collections_Generic_IEnumerable_TSource__o *)_main_1->fields.chapters; /*0x1d54e50*/
            if ( !ChapterSelector___c_TypeInfo->_2.cctor_finished ) /*0x1d54e54*/
            {
              j_il2cpp_runtime_class_init_0(v263, v264, v265); /*0x1d54e5c*/
              ChapterSelector.__c_TypeInfo_2 = ChapterSelector___c_TypeInfo; /*0x1d54e60*/
            }
            _9__18_5 = (System_Func_object__bool__o *)ChapterSelector.__c_TypeInfo_2->static_fields->__9__18_5; /*0x1d54e68*/
            if ( !_9__18_5 ) /*0x1d54e6c*/
            {
              if ( !ChapterSelector.__c_TypeInfo_2->_2.cctor_finished ) /*0x1d54e70*/
              {
                j_il2cpp_runtime_class_init_0(v263, v264, v265); /*0x1d54e78*/
                ChapterSelector.__c_TypeInfo_2 = ChapterSelector___c_TypeInfo; /*0x1d54e7c*/
              }
              __2 = (Il2CppObject *)ChapterSelector.__c_TypeInfo_2->static_fields->__9; /*0x1d54e84*/
              _9__18_5 = (System_Func_object__bool__o *)sub_1B624E8(System_Func_Chapter__bool__TypeInfo); /*0x1d54ea8*/
              System_Func_object__bool____ctor(_9__18_5, __2, Method_ChapterSelector___c__Start_b__18_5__, 0); /*0x1d54eb0*/
              ChapterSelector___c_TypeInfo->static_fields->__9__18_5 = (struct System_Func_Chapter__bool__o *)_9__18_5; /*0x1d54ebc*/
            }
            chapterPanel = (__int64)System_Linq_Enumerable__First_object__32951596( /*0x1d54ed4*/
                                      chapters_1,
                                      (System_Func_TSource__bool__o *)_9__18_5,
                                      (const MethodInfo_1F6CD2C *)Method_System_Linq_Enumerable_First_Chapter___);
            if ( !chapterPanel ) /*0x1d54ed8*/
              goto LABEL_410; /*0x1d54ed8*/
            v276 = *(_QWORD *)(chapterPanel + 24); /*0x1d54edc*/
            if ( !v276 ) /*0x1d54ee0*/
              goto LABEL_410; /*0x1d54ee0*/
            ChapterSelector.__c_TypeInfo_3 = ChapterSelector___c_TypeInfo; /*0x1d54ee4*/
            source_2 = *(System_Collections_Generic_IEnumerable_TSource__o **)(v276 + 48); /*0x1d54ee8*/
            if ( !ChapterSelector___c_TypeInfo->_2.cctor_finished ) /*0x1d54eec*/
            {
              j_il2cpp_runtime_class_init_0(v273, v274, v275); /*0x1d54ef4*/
              ChapterSelector.__c_TypeInfo_3 = ChapterSelector___c_TypeInfo; /*0x1d54ef8*/
            }
            _9__18_6 = (System_Func_object__object__o *)ChapterSelector.__c_TypeInfo_3->static_fields->__9__18_6; /*0x1d54f00*/
            if ( !_9__18_6 ) /*0x1d54f04*/
            {
              if ( !ChapterSelector.__c_TypeInfo_3->_2.cctor_finished ) /*0x1d54f08*/
              {
                j_il2cpp_runtime_class_init_0(v273, v274, v275); /*0x1d54f10*/
                ChapterSelector.__c_TypeInfo_3 = ChapterSelector___c_TypeInfo; /*0x1d54f14*/
              }
              __3 = (Il2CppObject *)ChapterSelector.__c_TypeInfo_3->static_fields->__9; /*0x1d54f1c*/
              _9__18_6 = (System_Func_object__object__o *)sub_1B624E8(System_Func_ChapterSongItem__string__TypeInfo); /*0x1d54f40*/
              System_Func_object__object____ctor(_9__18_6, __3, Method_ChapterSelector___c__Start_b__18_6__, 0); /*0x1d54f48*/
              ChapterSelector___c_TypeInfo->static_fields->__9__18_6 = (struct System_Func_ChapterSongItem__string__o *)_9__18_6; /*0x1d54f54*/
            }
            source_3 = (System_Collections_Generic_IEnumerable_TSource__o *)System_Linq_Enumerable__Select_object__object_( /*0x1d54f6c*/
                                                                              source_2,
                                                                              (System_Func_TSource__TResult__o *)_9__18_6,
                                                                              (const MethodInfo_1F747E0 *)Method_System_Linq_Enumerable_Select_ChapterSongItem__string___);
            chapterPanel = (__int64)System_Linq_Enumerable__ToArray_object_( /*0x1d54f7c*/
                                      source_3,
                                      (const MethodInfo_1F7ABAC *)Method_System_Linq_Enumerable_ToArray_string___);
            v338 = 0; /*0x1d54f80*/
            v339 = 0; /*0x1d54f80*/
            if ( !chapterPanel_5 ) /*0x1d54f84*/
              goto LABEL_410; /*0x1d54f84*/
            v282 = *(_QWORD *)(chapterPanel_5 + 24); /*0x1d54f88*/
            if ( (int)v282 >= 1 ) /*0x1d54f90*/
            {
              chapterPanel_6 = chapterPanel; /*0x1d54f94*/
              v284 = 0; /*0x1d54f98*/
              v335 = chapterPanel + 32; /*0x1d54fa8*/
              while ( v284 < (unsigned int)v282 ) /*0x1d54fb0*/
              {
                key = System_String__Concat_53483572( /*0x1d54fe0*/
                        (System_String_o *)"VRC8",
                        *(System_String_o **)(chapterPanel_5 + 32 + 8 * v284),
                        (System_String_o *)"Unlocked",
                        0);
                if ( !Phigros2_VisionReplay_VisionReplay_Process_TypeInfo->_2.cctor_finished ) /*0x1d54fe8*/
                  j_il2cpp_runtime_class_init_0(v286, v287, v288); /*0x1d54ff4*/
                chapterPanel = Phigros2_VisionReplay_VisionReplay_Process__GetValue(key, 0); /*0x1d55000*/
                if ( (chapterPanel & 1) != 0 ) /*0x1d55004*/
                {
                  ++HIDWORD(v339); /*0x1d55018*/
                  if ( !byte_446E372 ) /*0x1d5501c*/
                  {
                    chapterPanel = sub_1B622A0(&GameInformation_TypeInfo, method); /*0x1d55028*/
                    byte_446E372 = 1; /*0x1d55034*/
                  }
                  if ( !chapterPanel_6 ) /*0x1d55038*/
                    goto LABEL_410; /*0x1d55038*/
                  if ( v284 >= *(unsigned int *)(chapterPanel_6 + 24) ) /*0x1d55044*/
                    goto LABEL_454; /*0x1d55044*/
                  chapterPanel = (__int64)GameInformation_TypeInfo->static_fields->_main; /*0x1d55058*/
                  if ( !chapterPanel ) /*0x1d5505c*/
                    goto LABEL_410; /*0x1d5505c*/
                  chapterPanel = (__int64)GameInformation__GetSongAllRecord( /*0x1d5506c*/
                                            (GameInformation_o *)chapterPanel,
                                            *(System_String_o **)(v335 + 8 * v284),
                                            0);
                  if ( !chapterPanel ) /*0x1d55070*/
                    goto LABEL_410; /*0x1d55070*/
                  chapterPanel_7 = (System_Collections_Generic_List_object__o *)chapterPanel; /*0x1d5507c*/
                  chapterPanel = (__int64)System_Collections_Generic_List_object___get_Item( /*0x1d55080*/
                                            (System_Collections_Generic_List_object__o *)chapterPanel,
                                            this->fields._nowLevelIndex,
                                            (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_LevelRecord__get_Item__);
                  if ( !chapterPanel ) /*0x1d55084*/
                    goto LABEL_410; /*0x1d55084*/
                  if ( *(int *)(chapterPanel + 16) >= 1000000 ) /*0x1d55098*/
                  {
                    LODWORD(v338) = v338 + 1; /*0x1d55114*/
                    ++HIDWORD(v338); /*0x1d55114*/
                    LODWORD(v339) = v339 + 1; /*0x1d55118*/
                  }
                  else
                  {
                    chapterPanel = (__int64)System_Collections_Generic_List_object___get_Item( /*0x1d550a8*/
                                              chapterPanel_7,
                                              this->fields._nowLevelIndex,
                                              (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_LevelRecord__get_Item__);
                    if ( !chapterPanel ) /*0x1d550ac*/
                      goto LABEL_410; /*0x1d550ac*/
                    if ( *(int *)(chapterPanel + 16) >= 700000 ) /*0x1d550c0*/
                      LODWORD(v339) = v339 + 1; /*0x1d550cc*/
                    chapterPanel = (__int64)System_Collections_Generic_List_object___get_Item( /*0x1d550dc*/
                                              chapterPanel_7,
                                              this->fields._nowLevelIndex,
                                              (const MethodInfo_2E4F384 *)Method_System_Collections_Generic_List_LevelRecord__get_Item__);
                    if ( !chapterPanel ) /*0x1d550e0*/
                      goto LABEL_410; /*0x1d550e0*/
                    if ( *(_DWORD *)(chapterPanel + 24) == 1 ) /*0x1d550ec*/
                      ++HIDWORD(v338); /*0x1d550f8*/
                  }
                }
                LODWORD(v282) = *(_DWORD *)(chapterPanel_5 + 24); /*0x1d5511c*/
                if ( (__int64)++v284 >= (int)v282 ) /*0x1d55128*/
                  goto LABEL_382; /*0x1d55128*/
              }
              goto LABEL_454; /*0x1d54fb0*/
            }
LABEL_382:
            chapterInfos_35 = *p_chapterInfos; /*0x1d5512c*/
            if ( !*p_chapterInfos ) /*0x1d55130*/
              goto LABEL_410; /*0x1d55130*/
            if ( (unsigned int)max_length_3 >= LODWORD(chapterInfos_35->max_length) ) /*0x1d5513c*/
              goto LABEL_454; /*0x1d5513c*/
            v291 = chapterInfos_35->m_Items[max_length_3]; /*0x1d55144*/
            if ( !v291 ) /*0x1d55148*/
              goto LABEL_410; /*0x1d55148*/
            clearSongNum_1 = v291->fields.clearSongNum; /*0x1d5514c*/
            chapterPanel = (__int64)System_Int32__ToString((int32_t)&v339, 0); /*0x1d55158*/
            if ( !clearSongNum_1 ) /*0x1d5515c*/
              goto LABEL_410; /*0x1d5515c*/
            chapterPanel = ((__int64 (__fastcall *)(struct UnityEngine_UI_Text_o *, __int64, const MethodInfo *))clearSongNum_1->klass->vtable._75_set_text.methodPtr)( /*0x1d55174*/
                             clearSongNum_1,
                             chapterPanel,
                             clearSongNum_1->klass->vtable._75_set_text.method);
            chapterInfos_36 = *p_chapterInfos; /*0x1d55178*/
            if ( !*p_chapterInfos ) /*0x1d55178*/
              goto LABEL_410; /*0x1d5517c*/
            if ( (unsigned int)max_length_3 >= LODWORD(chapterInfos_36->max_length) ) /*0x1d55188*/
              goto LABEL_454; /*0x1d55188*/
            v294 = chapterInfos_36->m_Items[max_length_3]; /*0x1d55190*/
            if ( !v294 ) /*0x1d55194*/
              goto LABEL_410; /*0x1d55194*/
            fullComboSongNum_1 = v294->fields.fullComboSongNum; /*0x1d55198*/
            chapterPanel = (__int64)System_Int32__ToString((int32_t)&v338 + 4, 0); /*0x1d551a4*/
            if ( !fullComboSongNum_1 ) /*0x1d551a8*/
              goto LABEL_410; /*0x1d551a8*/
            chapterPanel = ((__int64 (__fastcall *)(struct UnityEngine_UI_Text_o *, __int64, const MethodInfo *))fullComboSongNum_1->klass->vtable._75_set_text.methodPtr)( /*0x1d551c0*/
                             fullComboSongNum_1,
                             chapterPanel,
                             fullComboSongNum_1->klass->vtable._75_set_text.method);
            chapterInfos_37 = *p_chapterInfos; /*0x1d551c4*/
            if ( !*p_chapterInfos ) /*0x1d551c4*/
              goto LABEL_410; /*0x1d551c8*/
            if ( (unsigned int)max_length_3 >= LODWORD(chapterInfos_37->max_length) ) /*0x1d551d4*/
              goto LABEL_454; /*0x1d551d4*/
            v297 = chapterInfos_37->m_Items[max_length_3]; /*0x1d551dc*/
            if ( !v297 ) /*0x1d551e0*/
              goto LABEL_410; /*0x1d551e0*/
            phiSongNum_1 = v297->fields.phiSongNum; /*0x1d551e4*/
            chapterPanel = (__int64)System_Int32__ToString((int32_t)&v338, 0); /*0x1d551f0*/
            if ( !phiSongNum_1 ) /*0x1d551f4*/
              goto LABEL_410; /*0x1d551f4*/
            chapterPanel = ((__int64 (__fastcall *)(struct UnityEngine_UI_Text_o *, __int64, const MethodInfo *))phiSongNum_1->klass->vtable._75_set_text.methodPtr)( /*0x1d5520c*/
                             phiSongNum_1,
                             chapterPanel,
                             phiSongNum_1->klass->vtable._75_set_text.method);
          }
          else
          {
LABEL_395:
            HIDWORD(v339) = 0; /*0x1d55214*/
            if ( !chapterPanel_5 ) /*0x1d55218*/
              goto LABEL_410; /*0x1d55218*/
            v299 = *(_QWORD *)(chapterPanel_5 + 24); /*0x1d5521c*/
            if ( (int)v299 >= 1 ) /*0x1d55224*/
            {
              v300 = 0; /*0x1d55228*/
              while ( v300 < (unsigned int)v299 ) /*0x1d55238*/
              {
                keyName_3 = System_String__Concat_53483572( /*0x1d55268*/
                              (System_String_o *)"C8",
                              *(System_String_o **)(chapterPanel_5 + 32 + 8 * v300),
                              (System_String_o *)"Unlocked",
                              0);
                if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d55270*/
                  j_il2cpp_runtime_class_init_0(v302, v303, v304); /*0x1d5527c*/
                chapterPanel = SaveManagement__LoadBool(keyName_3, 0, 0); /*0x1d5528c*/
                if ( (chapterPanel & 1) != 0 ) /*0x1d55290*/
                  ++HIDWORD(v339); /*0x1d5529c*/
                LODWORD(v299) = *(_DWORD *)(chapterPanel_5 + 24); /*0x1d552a0*/
                if ( (__int64)++v300 >= (int)v299 ) /*0x1d552ac*/
                  goto LABEL_404; /*0x1d552ac*/
              }
              goto LABEL_454; /*0x1d55238*/
            }
          }
LABEL_404:
          chapterInfos_38 = *p_chapterInfos; /*0x1d552b0*/
          if ( !*p_chapterInfos ) /*0x1d552b4*/
            goto LABEL_410; /*0x1d552b4*/
          if ( (unsigned int)max_length_3 >= LODWORD(chapterInfos_38->max_length) ) /*0x1d552c8*/
            goto LABEL_454; /*0x1d552c8*/
          v306 = chapterInfos_38->m_Items[max_length_3]; /*0x1d552d0*/
          if ( !v306 ) /*0x1d552d4*/
            goto LABEL_410; /*0x1d552d4*/
          allSongNum_1 = v306->fields.allSongNum; /*0x1d552d8*/
          chapterPanel = (__int64)System_Int32__ToString((int32_t)&v339 + 4, 0); /*0x1d552e4*/
          if ( !allSongNum_1 ) /*0x1d552e8*/
            goto LABEL_410; /*0x1d552e8*/
          chapterPanel = ((__int64 (__fastcall *)(struct UnityEngine_UI_Text_o *, __int64, const MethodInfo *))allSongNum_1->klass->vtable._75_set_text.methodPtr)( /*0x1d55300*/
                           allSongNum_1,
                           chapterPanel,
                           allSongNum_1->klass->vtable._75_set_text.method);
        }
      }
      chapterInfos_28 = *p_chapterInfos; /*0x1d55304*/
      ++max_length_3; /*0x1d55308*/
      if ( !*p_chapterInfos ) /*0x1d55304*/
        goto LABEL_410; /*0x1d5530c*/
    }
    v337.fields.i = 0; /*0x1d55318*/
    if ( (int)max_length_2 >= 1 ) /*0x1d5531c*/
    {
      n4_1 = 4; /*0x1d55344*/
      while ( 1 ) /*0x1d5534c*/
      {
        v309 = n4_1 - 4; /*0x1d5534c*/
        if ( (int)n4_1 - 4 >= (unsigned int)max_length_2 ) /*0x1d55354*/
          goto LABEL_454; /*0x1d55354*/
        v310 = *((_QWORD *)&chapterInfos_28->obj.klass + n4_1); /*0x1d55358*/
        if ( !v310 ) /*0x1d5535c*/
          goto LABEL_410; /*0x1d5535c*/
        v311 = *(_QWORD *)(v310 + 48); /*0x1d55360*/
        if ( !v311 ) /*0x1d55364*/
          goto LABEL_410; /*0x1d55364*/
        unlockFlag = *(struct System_String_o **)(v311 + 24); /*0x1d55368*/
        v336.fields.unlockFlag = unlockFlag; /*0x1d5536c*/
        switch ( *(_DWORD *)(v311 + 16) ) /*0x1d5538c*/
        {
          case 1: /*0x1d5538c*/
            goto LABEL_425;
          case 2: /*0x1d5538c*/
            LegacyChapterSongFinishNum_18_10 = ChapterSelector___Start_g__GetLegacyChapterSongFinishNum_18_10((const MethodInfo *)chapterPanel); /*0x1d55394*/
            LegacyChapterSongFinishNum_18 = System_Int32__Parse(unlockFlag, 0); /*0x1d553ac*/
            if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d553b4*/
              j_il2cpp_runtime_class_init_0(v315, v316, v317); /*0x1d553c0*/
            SaveManagement__SaveBool( /*0x1d553d4*/
              (System_String_o *)"finishLegacyChapter",
              LegacyChapterSongFinishNum_18_10 >= LegacyChapterSongFinishNum_18,
              0);
            ChapterSelector___Start_g__SetChapterUnlockTipCase2_18_9( /*0x1d553e8*/
              this,
              LegacyChapterSongFinishNum_18_10,
              &v337,
              &v336,
              method_1);
            if ( LegacyChapterSongFinishNum_18_10 >= LegacyChapterSongFinishNum_18 ) /*0x1d553f0*/
              goto LABEL_451; /*0x1d553f0*/
            goto LABEL_435; /*0x1d553f0*/
          case 3: /*0x1d5538c*/
            IsSpecifiedSongReachScore_18_7 = ChapterSelector___Start_g__IsSpecifiedSongReachScore_18_7( /*0x1d55404*/
                                               unlockFlag,
                                               920000,
                                               method_2);
            "ChapterUnlockTip92w" = (System_String_o *)"ChapterUnlockTip92w"; /*0x1d55408*/
            goto LABEL_426; /*0x1d5540c*/
          case 4: /*0x1d5538c*/
            if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d5541c*/
              j_il2cpp_runtime_class_init_0(v209, v210, v211); /*0x1d55424*/
            chapterPanel = SaveManagement__LoadBool((System_String_o *)"ReadSideStory4Begin", 0, 0); /*0x1d55434*/
            if ( (chapterPanel & 1) != 0 ) /*0x1d55438*/
            {
LABEL_425:
              IsSpecifiedSongReachScore_18_7 = ChapterSelector___Start_g__IsSpecifiedSongReachScore_18_7( /*0x1d5543c*/
                                                 unlockFlag,
                                                 880000,
                                                 method_2);
              "ChapterUnlockTip92w" = (System_String_o *)"MainStoryChapterUnlockTip"; /*0x1d5544c*/
LABEL_426:
              IsSpecifiedSongReachScore_18 = IsSpecifiedSongReachScore_18_7; /*0x1d55450*/
              ChapterSelector___Start_g__SetChapterUnlockTip_18_8(this, "ChapterUnlockTip92w", &v337, &v336, method_3); /*0x1d55460*/
              if ( !IsSpecifiedSongReachScore_18 ) /*0x1d55464*/
                goto LABEL_451; /*0x1d55464*/
            }
            else
            {
              chapterInfos_39 = *p_chapterInfos; /*0x1d5546c*/
              if ( !*p_chapterInfos ) /*0x1d55470*/
                goto LABEL_410; /*0x1d55470*/
              if ( v309 >= LODWORD(chapterInfos_39->max_length) ) /*0x1d5547c*/
                goto LABEL_454; /*0x1d5547c*/
              v327 = *((_QWORD *)&chapterInfos_39->obj.klass + n4_1); /*0x1d55480*/
              if ( !v327 ) /*0x1d55484*/
                goto LABEL_410; /*0x1d55484*/
              v328 = *(_QWORD *)(v327 + 112); /*0x1d55490*/
              if ( !MultiLanguageTextBase_TypeInfo->_2.cctor_finished ) /*0x1d55498*/
                j_il2cpp_runtime_class_init_0(v322, v323, v324); /*0x1d554a0*/
              chapterPanel = (__int64)MultiLanguageTextBase__GetNowLanguageText( /*0x1d554b4*/
                                        (System_String_o *)"SideStory4UnlockTip",
                                        0);
              if ( !v328 ) /*0x1d554b8*/
                goto LABEL_410; /*0x1d554b8*/
              chapterPanel = (*(__int64 (__fastcall **)(__int64, __int64, _QWORD))(*(_QWORD *)v328 + 1512LL))( /*0x1d554d0*/
                               v328,
                               chapterPanel,
                               *(_QWORD *)(*(_QWORD *)v328 + 1520LL));
            }
LABEL_435:
            chapterInfos_40 = *p_chapterInfos; /*0x1d554d4*/
            if ( !*p_chapterInfos ) /*0x1d554d8*/
              goto LABEL_410; /*0x1d554d8*/
            if ( v309 >= LODWORD(chapterInfos_40->max_length) ) /*0x1d554e4*/
              goto LABEL_454; /*0x1d554e4*/
            v330 = *((_QWORD *)&chapterInfos_40->obj.klass + n4_1); /*0x1d554e8*/
            if ( !v330 ) /*0x1d554ec*/
              goto LABEL_410; /*0x1d554ec*/
            chapterPanel = *(_QWORD *)(v330 + 128); /*0x1d554f0*/
            if ( !chapterPanel ) /*0x1d554f4*/
              goto LABEL_410; /*0x1d554f4*/
            chapterPanel = (__int64)UnityEngine_Component__get_gameObject((UnityEngine_Component_o *)chapterPanel, 0); /*0x1d554fc*/
            if ( !chapterPanel ) /*0x1d55500*/
              goto LABEL_410; /*0x1d55500*/
            UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)chapterPanel, 0, 0); /*0x1d5550c*/
            chapterInfos_41 = *p_chapterInfos; /*0x1d55510*/
            if ( !*p_chapterInfos ) /*0x1d55510*/
              goto LABEL_410; /*0x1d55514*/
            if ( v309 >= LODWORD(chapterInfos_41->max_length) ) /*0x1d55520*/
              goto LABEL_454; /*0x1d55520*/
            v332 = *((_QWORD *)&chapterInfos_41->obj.klass + n4_1); /*0x1d55524*/
            if ( !v332 ) /*0x1d55528*/
              goto LABEL_410; /*0x1d55528*/
            chapterPanel = *(_QWORD *)(v332 + 120); /*0x1d5552c*/
            if ( !chapterPanel ) /*0x1d55530*/
              goto LABEL_410; /*0x1d55530*/
            chapterPanel = (__int64)UnityEngine_GameObject__get_gameObject((UnityEngine_GameObject_o *)chapterPanel, 0); /*0x1d55538*/
            if ( !chapterPanel ) /*0x1d5553c*/
              goto LABEL_410; /*0x1d5553c*/
            UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)chapterPanel, 0, 0); /*0x1d55548*/
            chapterInfos_42 = *p_chapterInfos; /*0x1d5554c*/
            if ( !*p_chapterInfos ) /*0x1d5554c*/
              goto LABEL_410; /*0x1d55550*/
            if ( v309 >= LODWORD(chapterInfos_42->max_length) ) /*0x1d5555c*/
              goto LABEL_454; /*0x1d5555c*/
            v334 = *((_QWORD *)&chapterInfos_42->obj.klass + n4_1); /*0x1d55560*/
            if ( !v334 ) /*0x1d55564*/
              goto LABEL_410; /*0x1d55564*/
            chapterPanel = *(_QWORD *)(v334 + 104); /*0x1d55568*/
            if ( !chapterPanel ) /*0x1d5556c*/
              goto LABEL_410; /*0x1d5556c*/
            chapterPanel = (__int64)UnityEngine_GameObject__get_gameObject((UnityEngine_GameObject_o *)chapterPanel, 0); /*0x1d55574*/
            if ( !chapterPanel ) /*0x1d55578*/
              goto LABEL_410; /*0x1d55578*/
            UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)chapterPanel, 1, 0); /*0x1d55584*/
LABEL_451:
            v337.fields.i = n4_1 - 3; /*0x1d55588*/
            chapterInfos_28 = *p_chapterInfos; /*0x1d55590*/
            if ( !*p_chapterInfos ) /*0x1d55594*/
              goto LABEL_410; /*0x1d55594*/
            LODWORD(max_length_2) = chapterInfos_28->max_length; /*0x1d55598*/
            if ( (int)++n4_1 - 4 >= (int)max_length_2 ) /*0x1d555a8*/
              return; /*0x1d555a8*/
            break; /*0x1d555a8*/
          default:
            goto LABEL_451;
        }
      }
    }
  }
  else
  {
    if ( !UnityEngine_Debug_TypeInfo->_2.cctor_finished ) /*0x1d54498*/
      j_il2cpp_runtime_class_init_0(v65, v66, v67); /*0x1d544a4*/
    UnityEngine_Debug__LogError((Il2CppObject *)"Can't find any ChapterInfo.", 0); /*0x1d544b0*/
  }
}