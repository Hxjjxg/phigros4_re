                            goto LABEL_410; /*0x1d53d80*/
                          *(_QWORD *)(v120 + 64) = *(_QWORD *)(v118 + 56); /*0x1d53d88*/
                        }
                      }
                    }
                  }
                  if ( System_String__op_Equality( /*0x1d53da0*/
                         *(System_String_o **)(object + 24),
                         (System_String_o *)"MainStory8",
                         0) )
                  {
                    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d53db4*/
                      j_il2cpp_runtime_class_init_0(v121, v122, v123); /*0x1d53dbc*/
                    chapterPanel = SaveManagement__LoadBool((System_String_o *)"chapter8Passed", 0, 0); /*0x1d53dd4*/
                    if ( (chapterPanel & 1) != 0 ) /*0x1d53dd8*/
                    {
                      chapterInfos_10 = *p_chapterInfos; /*0x1d53ddc*/
                      if ( !*p_chapterInfos ) /*0x1d53de0*/
                        goto LABEL_410; /*0x1d53de0*/
                      if ( v72 >= LODWORD(chapterInfos_10->max_length) ) /*0x1d53dec*/
                        goto LABEL_454; /*0x1d53dec*/
                      v128 = *((_QWORD *)&chapterInfos_10->obj.klass + n4); /*0x1d53df0*/
                      chapterPanel = (__int64)IdBuilder__BuildBackgroundImageId( /*0x1d53e04*/
                                                (System_String_o *)"MainStory8_2",
                                                0);
                      if ( !v128 ) /*0x1d53e08*/
                        goto LABEL_410; /*0x1d53e08*/
                      *(_QWORD *)(v128 + 56) = chapterPanel; /*0x1d53e0c*/
                      chapterInfos_11 = *p_chapterInfos; /*0x1d53e10*/
                      if ( !*p_chapterInfos ) /*0x1d53e10*/
                        goto LABEL_410; /*0x1d53e14*/
                      if ( v72 >= LODWORD(chapterInfos_11->max_length) ) /*0x1d53e20*/
                        goto LABEL_454; /*0x1d53e20*/
                      v130 = *((_QWORD *)&chapterInfos_11->obj.klass + n4); /*0x1d53e24*/
                      chapterPanel = (__int64)IdBuilder__BuildBlurBackgroundImageId( /*0x1d53e38*/
                                                (System_String_o *)"MainStory8_2",
                                                0);
                      if ( !v130 ) /*0x1d53e3c*/
                        goto LABEL_410; /*0x1d53e3c*/
                      *(_QWORD *)(v130 + 64) = chapterPanel; /*0x1d53e40*/
                    }
                    else
                    {
                      if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d53e54*/
                        j_il2cpp_runtime_class_init_0(v124, v125, v126); /*0x1d53e5c*/
                      chapterPanel = SaveManagement__LoadBool((System_String_o *)"chapter8UnlockSecondPhase", 0, 0); /*0x1d53e74*/
                      chapterInfos_12 = *p_chapterInfos; /*0x1d53e78*/
                      if ( !*p_chapterInfos ) /*0x1d53e7c*/
                        goto LABEL_410; /*0x1d53e7c*/
                      if ( v72 >= LODWORD(chapterInfos_12->max_length) ) /*0x1d53e88*/
                        goto LABEL_454; /*0x1d53e88*/
                      v135 = *((_QWORD *)&chapterInfos_12->obj.klass + n4); /*0x1d53e8c*/
                      if ( (chapterPanel & 1) != 0 ) /*0x1d53e90*/
                      {
                        chapterPanel = (__int64)IdBuilder__BuildBackgroundImageId( /*0x1d53ea4*/
                                                  (System_String_o *)"MainStory8_1",
                                                  0);
                        if ( !v135 ) /*0x1d53ea8*/
                          goto LABEL_410; /*0x1d53ea8*/
                        *(_QWORD *)(v135 + 56) = chapterPanel; /*0x1d53eac*/
                        chapterInfos_13 = *p_chapterInfos; /*0x1d53eb0*/
                        if ( !*p_chapterInfos ) /*0x1d53eb0*/
                          goto LABEL_410; /*0x1d53eb4*/
                        if ( v72 >= LODWORD(chapterInfos_13->max_length) ) /*0x1d53ec0*/
                          goto LABEL_454; /*0x1d53ec0*/
                        v137 = *((_QWORD *)&chapterInfos_13->obj.klass + n4); /*0x1d53ec4*/
                        p_StringLiteral_6258 = &"MainStory8_1"; /*0x1d53ecc*/
                      }
                      else
                      {
                        chapterPanel = (__int64)IdBuilder__BuildBackgroundImageId( /*0x1d53ee4*/
                                                  (System_String_o *)"MainStory8_0",
                                                  0);
                        if ( !v135 ) /*0x1d53ee8*/
                          goto LABEL_410; /*0x1d53ee8*/
                        *(_QWORD *)(v135 + 56) = chapterPanel; /*0x1d53eec*/
                        chapterInfos_14 = *p_chapterInfos; /*0x1d53ef0*/
                        if ( !*p_chapterInfos ) /*0x1d53ef0*/
                          goto LABEL_410; /*0x1d53ef4*/
                        if ( v72 >= LODWORD(chapterInfos_14->max_length) ) /*0x1d53f00*/
                          goto LABEL_454; /*0x1d53f00*/
                        v137 = *((_QWORD *)&chapterInfos_14->obj.klass + n4); /*0x1d53f04*/
                        p_StringLiteral_6258 = &"MainStory8_0"; /*0x1d53f0c*/
                      }
                      chapterPanel = (__int64)IdBuilder__BuildBlurBackgroundImageId( /*0x1d53f18*/
                                                (System_String_o *)*p_StringLiteral_6258,
                                                0);
                      if ( !v137 ) /*0x1d53f1c*/
                        goto LABEL_410; /*0x1d53f1c*/
                      *(_QWORD *)(v137 + 64) = chapterPanel; /*0x1d53f20*/
                      chapterPanel = (__int64)this->fields.chapter8AllNumTitle; /*0x1d53f24*/
                      if ( !chapterPanel ) /*0x1d53f28*/
                        goto LABEL_410; /*0x1d53f28*/
                      (*(void (__fastcall **)(__int64, __int64, _QWORD))(*(_QWORD *)chapterPanel + 1512LL))( /*0x1d53f44*/
                        chapterPanel,
                        "Deciphered",
                        *(_QWORD *)(*(_QWORD *)chapterPanel + 1520LL));
                    }
                    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d53f5c*/
                      j_il2cpp_runtime_class_init_0(v131, v132, v133); /*0x1d53f64*/
                    if ( SaveManagement__LoadBool((System_String_o *)"IsSimulateMode", 1, 0) ) /*0x1d53f7c*/
                    {
                      chapterPanel = (__int64)this->fields.chapter8StartButtonText; /*0x1d53f84*/
                      if ( !chapterPanel ) /*0x1d53f88*/
                        goto LABEL_410; /*0x1d53f88*/
                      (*(void (__fastcall **)(__int64, __int64, _QWORD))(*(_QWORD *)chapterPanel + 1512LL))( /*0x1d53fa4*/
                        chapterPanel,
                        "B<size=24> </size>O<size=24> </size>O<size=24> </size>T",
                        *(_QWORD *)(*(_QWORD *)chapterPanel + 1520LL));
                    }
                    if ( !byte_446E376 ) /*0x1d53fb0*/
                    {
                      sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_TypeInfo, method); /*0x1d53fbc*/
                      byte_446E376 = 1; /*0x1d53fc8*/
                    }
                    static_fields = Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields; /*0x1d53fd8*/
                    if ( static_fields->_IsOn_k__BackingField ) /*0x1d53fdc*/
                    {
                      if ( !byte_446E377 ) /*0x1d53fec*/
                      {
                        sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_TypeInfo, method); /*0x1d53ffc*/
                        byte_446E377 = 1; /*0x1d54014*/
                        static_fields = Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields; /*0x1d54018*/
                      }
                      if ( static_fields->_IsSimulateMode_k__BackingField ) /*0x1d5401c*/
                      {
                        if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished ) /*0x1d54030*/
                          j_il2cpp_runtime_class_init_0(v140, v141, v142); /*0x1d54038*/
                        if ( !byte_446E378 ) /*0x1d54044*/
                        {
                          sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, method); /*0x1d5404c*/
                          byte_446E378 = 1; /*0x1d54058*/
                        }
                        Phigros2.VisionReplay.VisionReplay.Key_TypeInfo = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo; /*0x1d5405c*/
                        if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished ) /*0x1d54060*/
                        {
                          j_il2cpp_runtime_class_init_0(v140, v141, v142); /*0x1d54068*/
                          Phigros2.VisionReplay.VisionReplay.Key_TypeInfo = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo; /*0x1d5406c*/
                        }
                        Chapter8Passed_k__BackingField = Phigros2.VisionReplay.VisionReplay.Key_TypeInfo->static_fields->_Chapter8Passed_k__BackingField; /*0x1d54080*/
                        if ( !Phigros2_VisionReplay_VisionReplay_Process_TypeInfo->_2.cctor_finished ) /*0x1d54084*/
                          j_il2cpp_runtime_class_init_0(v140, v141, v142); /*0x1d54090*/
                        chapterPanel = Phigros2_VisionReplay_VisionReplay_Process__GetValue( /*0x1d5409c*/
                                         Chapter8Passed_k__BackingField,
                                         0);
                        if ( (chapterPanel & 1) != 0 ) /*0x1d540a0*/
                        {
                          chapterInfos_15 = *p_chapterInfos; /*0x1d540a4*/
                          if ( !*p_chapterInfos ) /*0x1d540a8*/
                            goto LABEL_410; /*0x1d540a8*/
                          if ( v72 >= LODWORD(chapterInfos_15->max_length) ) /*0x1d540b4*/
                            goto LABEL_454; /*0x1d540b4*/
                          v150 = *((_QWORD *)&chapterInfos_15->obj.klass + n4); /*0x1d540b8*/
                          chapterPanel = (__int64)IdBuilder__BuildBackgroundImageId( /*0x1d540cc*/
                                                    (System_String_o *)"MainStory8_2",
                                                    0);
                          if ( !v150 ) /*0x1d540d0*/
                            goto LABEL_410; /*0x1d540d0*/
                          *(_QWORD *)(v150 + 56) = chapterPanel; /*0x1d540d4*/
                          chapterInfos_16 = *p_chapterInfos; /*0x1d540d8*/
                          if ( !*p_chapterInfos ) /*0x1d540d8*/
                            goto LABEL_410; /*0x1d540dc*/
                          if ( v72 >= LODWORD(chapterInfos_16->max_length) ) /*0x1d540e8*/
                            goto LABEL_454; /*0x1d540e8*/
                          v152 = *((_QWORD *)&chapterInfos_16->obj.klass + n4); /*0x1d540ec*/
                          chapterPanel = (__int64)IdBuilder__BuildBlurBackgroundImageId( /*0x1d54100*/
                                                    (System_String_o *)"MainStory8_2",
                                                    0);
                          if ( !v152 ) /*0x1d54104*/
                            goto LABEL_410; /*0x1d54104*/
                          *(_QWORD *)(v152 + 64) = chapterPanel; /*0x1d54108*/
                        }
                        else
                        {
                          if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished ) /*0x1d54114*/
                            j_il2cpp_runtime_class_init_0(v146, v147, v148); /*0x1d5411c*/
                          if ( !byte_446E414 ) /*0x1d54128*/
                          {
                            sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, method); /*0x1d54130*/
                            byte_446E414 = 1; /*0x1d5413c*/
                          }
                          Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1 = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo; /*0x1d54140*/
                          if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished ) /*0x1d54144*/
                          {
                            j_il2cpp_runtime_class_init_0(v146, v147, v148); /*0x1d5414c*/
                            Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1 = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo; /*0x1d54150*/
                          }
                          Chapter8UnlockSecondPhase_k__BackingField = Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1->static_fields->_Chapter8UnlockSecondPhase_k__BackingField; /*0x1d54164*/
                          if ( !Phigros2_VisionReplay_VisionReplay_Process_TypeInfo->_2.cctor_finished ) /*0x1d54168*/
                            j_il2cpp_runtime_class_init_0(v146, v147, v148); /*0x1d54174*/
                          chapterPanel = Phigros2_VisionReplay_VisionReplay_Process__GetValue( /*0x1d54180*/
                                           Chapter8UnlockSecondPhase_k__BackingField,
                                           0);
                          chapterInfos_17 = *p_chapterInfos; /*0x1d54184*/
                          if ( !*p_chapterInfos ) /*0x1d54188*/
                            goto LABEL_410; /*0x1d54188*/
                          if ( v72 >= LODWORD(chapterInfos_17->max_length) ) /*0x1d54194*/
                            goto LABEL_454; /*0x1d54194*/
                          v156 = *((_QWORD *)&chapterInfos_17->obj.klass + n4); /*0x1d54198*/
                          if ( (chapterPanel & 1) != 0 ) /*0x1d5419c*/
                          {
                            chapterPanel = (__int64)IdBuilder__BuildBackgroundImageId( /*0x1d541b0*/
                                                      (System_String_o *)"MainStory8_1",
                                                      0);
                            if ( !v156 ) /*0x1d541b4*/
                              goto LABEL_410; /*0x1d541b4*/
                            *(_QWORD *)(v156 + 56) = chapterPanel; /*0x1d541b8*/
                            chapterInfos_18 = *p_chapterInfos; /*0x1d541bc*/
                            if ( !*p_chapterInfos ) /*0x1d541bc*/
                              goto LABEL_410; /*0x1d541c0*/
                            if ( v72 >= LODWORD(chapterInfos_18->max_length) ) /*0x1d541cc*/
                              goto LABEL_454; /*0x1d541cc*/
                            v158 = *((_QWORD *)&chapterInfos_18->obj.klass + n4); /*0x1d541d0*/
                            p_StringLiteral_6258_1 = &"MainStory8_1"; /*0x1d541d8*/
                          }
                          else
                          {
                            chapterPanel = (__int64)IdBuilder__BuildBackgroundImageId( /*0x1d541f0*/
                                                      (System_String_o *)"MainStory8_0",
                                                      0);
                            if ( !v156 ) /*0x1d541f4*/
                              goto LABEL_410; /*0x1d541f4*/
                            *(_QWORD *)(v156 + 56) = chapterPanel; /*0x1d541f8*/
                            chapterInfos_19 = *p_chapterInfos; /*0x1d541fc*/
                            if ( !*p_chapterInfos ) /*0x1d541fc*/
                              goto LABEL_410; /*0x1d54200*/
                            if ( v72 >= LODWORD(chapterInfos_19->max_length) ) /*0x1d5420c*/
                              goto LABEL_454; /*0x1d5420c*/
                            v158 = *((_QWORD *)&chapterInfos_19->obj.klass + n4); /*0x1d54210*/
                            p_StringLiteral_6258_1 = &"MainStory8_0"; /*0x1d54218*/
                          }
                          chapterPanel = (__int64)IdBuilder__BuildBlurBackgroundImageId( /*0x1d54224*/
                                                    (System_String_o *)*p_StringLiteral_6258_1,
                                                    0);
                          if ( !v158 ) /*0x1d54228*/
                            goto LABEL_410; /*0x1d54228*/
                          *(_QWORD *)(v158 + 64) = chapterPanel; /*0x1d5422c*/
                          chapterPanel = (__int64)this->fields.chapter8AllNumTitle; /*0x1d54230*/
                          if ( !chapterPanel ) /*0x1d54234*/
                            goto LABEL_410; /*0x1d54234*/
                          (*(void (__fastcall **)(__int64, __int64, _QWORD))(*(_QWORD *)chapterPanel + 1512LL))( /*0x1d54250*/
                            chapterPanel,
                            "Deciphered",
                            *(_QWORD *)(*(_QWORD *)chapterPanel + 1520LL));
                        }
                        chapterPanel = (__int64)this->fields.chapter8StartButtonText; /*0x1d5425c*/
                        if ( !chapterPanel ) /*0x1d54260*/
                          goto LABEL_410; /*0x1d54260*/
                        (*(void (__fastcall **)(__int64, __int64, _QWORD))(*(_QWORD *)chapterPanel + 1512LL))( /*0x1d5427c*/
                          chapterPanel,
                          "B<size=24> </size>O<size=24> </size>O<size=24> </size>T",
                          *(_QWORD *)(*(_QWORD *)chapterPanel + 1520LL));
                      }
                    }
                  }
                  chapterPanel = System_String__op_Equality( /*0x1d54294*/
                                   *(System_String_o **)(object + 24),
                                   (System_String_o *)"MainStory9",
                                   0);
                  if ( (chapterPanel & 1) != 0 ) /*0x1d54298*/
                  {
                    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d542a8*/
                      j_il2cpp_runtime_class_init_0(v161, v162, v163); /*0x1d542b0*/
                    chapterPanel = SaveManagement__LoadBool((System_String_o *)"Chapter9Phase2Passed", 0, 0); /*0x1d542c8*/
                    if ( (chapterPanel & 1) != 0 ) /*0x1d542cc*/
                    {
                      chapterInfos_20 = *p_chapterInfos; /*0x1d542d0*/
                      if ( !*p_chapterInfos ) /*0x1d542d4*/
                        goto LABEL_410; /*0x1d542d4*/
                      if ( v72 >= LODWORD(chapterInfos_20->max_length) ) /*0x1d542e0*/
                        goto LABEL_454; /*0x1d542e0*/
                      v165 = *((_QWORD *)&chapterInfos_20->obj.klass + n4); /*0x1d542e4*/
                      chapterPanel = (__int64)IdBuilder__BuildBackgroundImageId( /*0x1d542f8*/
                                                (System_String_o *)"MainStory9_2",
                                                0);
                      if ( !v165 ) /*0x1d542fc*/
                        goto LABEL_410; /*0x1d542fc*/
                      *(_QWORD *)(v165 + 56) = chapterPanel; /*0x1d54300*/
                      chapterInfos_21 = *p_chapterInfos; /*0x1d54304*/
                      if ( !*p_chapterInfos ) /*0x1d54304*/
                        goto LABEL_410; /*0x1d54308*/
                      if ( v72 >= LODWORD(chapterInfos_21->max_length) ) /*0x1d54314*/
                        goto LABEL_454; /*0x1d54314*/
                      v167 = *((_QWORD *)&chapterInfos_21->obj.klass + n4); /*0x1d54318*/
                      chapterPanel = (__int64)IdBuilder__BuildBlurBackgroundImageId( /*0x1d5432c*/
                                                (System_String_o *)"MainStory9_2",
                                                0);
                      if ( !v167 ) /*0x1d54330*/
                        goto LABEL_410; /*0x1d54330*/
                      *(_QWORD *)(v167 + 64) = chapterPanel; /*0x1d54334*/
                    }
                  }
                }
              }
            }
          }
          chapterInfos_22 = *p_chapterInfos; /*0x1d54340*/
          if ( !*p_chapterInfos ) /*0x1d54344*/
            goto LABEL_410; /*0x1d54344*/
          if ( v72 >= LODWORD(chapterInfos_22->max_length) ) /*0x1d54350*/
            goto LABEL_454; /*0x1d54350*/
          v169 = *((_QWORD *)&chapterInfos_22->obj.klass + n4); /*0x1d54354*/
          if ( !v169 ) /*0x1d54358*/
            goto LABEL_410; /*0x1d54358*/
          chapterPanel = *(_QWORD *)(v169 + 88); /*0x1d5435c*/
          if ( !chapterPanel ) /*0x1d54360*/
            goto LABEL_410; /*0x1d54360*/
          AddressableImageLoader__set_Code( /*0x1d5436c*/
            (AddressableImageLoader_o *)chapterPanel,
            *(System_String_o **)(v169 + 64),
            0);
          chapterInfos_23 = *p_chapterInfos; /*0x1d54370*/
          if ( !*p_chapterInfos ) /*0x1d54370*/
            goto LABEL_410; /*0x1d54374*/
          if ( v72 >= LODWORD(chapterInfos_23->max_length) ) /*0x1d54380*/
            goto LABEL_454; /*0x1d54380*/
          v171 = *((_QWORD *)&chapterInfos_23->obj.klass + n4); /*0x1d54384*/
          if ( !v171 ) /*0x1d54388*/
            goto LABEL_410; /*0x1d54388*/
          chapterPanel = *(_QWORD *)(v171 + 80); /*0x1d5438c*/
          if ( !chapterPanel ) /*0x1d54390*/
            goto LABEL_410; /*0x1d54390*/
          AddressableImageLoaderWithLowResPreview__SetLowResImage( /*0x1d5439c*/
            (AddressableImageLoaderWithLowResPreview_o *)chapterPanel,
            *(System_String_o **)(v171 + 64),
            0);
          chapterInfos_24 = *p_chapterInfos; /*0x1d543a0*/
          if ( !*p_chapterInfos ) /*0x1d543a0*/
            goto LABEL_410; /*0x1d543a4*/
          if ( v72 >= LODWORD(chapterInfos_24->max_length) ) /*0x1d543b0*/
            goto LABEL_454; /*0x1d543b0*/
          v173 = *((_QWORD *)&chapterInfos_24->obj.klass + n4); /*0x1d543b4*/
          if ( !v173 ) /*0x1d543b8*/
            goto LABEL_410; /*0x1d543b8*/
          chapterPanel = *(_QWORD *)(v173 + 72); /*0x1d543bc*/
          if ( !chapterPanel ) /*0x1d543c0*/
            goto LABEL_410; /*0x1d543c0*/
          chapterPanel = UnityEngine_Animation__Play_62936176( /*0x1d543cc*/
                           (UnityEngine_Animation_o *)chapterPanel,
                           (System_String_o *)"ChapterOpen",
                           0);
          chapterInfos_25 = *p_chapterInfos; /*0x1d543d0*/
          if ( !*p_chapterInfos ) /*0x1d543d0*/
            goto LABEL_410; /*0x1d543d4*/
          if ( v72 >= LODWORD(chapterInfos_25->max_length) ) /*0x1d543e0*/
            goto LABEL_454; /*0x1d543e0*/
          v175 = *((_QWORD *)&chapterInfos_25->obj.klass + n4); /*0x1d543e4*/
          if ( !v175 ) /*0x1d543e8*/
            goto LABEL_410; /*0x1d543e8*/
          chapterPanel = *(_QWORD *)(v175 + 72); /*0x1d543ec*/
          if ( !chapterPanel ) /*0x1d543f0*/
            goto LABEL_410; /*0x1d543f0*/
          chapterPanel = (__int64)UnityEngine_Animation__get_Item( /*0x1d543fc*/
                                    (UnityEngine_Animation_o *)chapterPanel,
                                    (System_String_o *)"ChapterOpen",
                                    0);
          if ( !chapterPanel ) /*0x1d54400*/
            goto LABEL_410; /*0x1d54400*/
          UnityEngine_AnimationState__set_speed((UnityEngine_AnimationState_o *)chapterPanel, 0.0, 0); /*0x1d5440c*/
          chapterInfos_26 = *p_chapterInfos; /*0x1d54410*/
          if ( !*p_chapterInfos ) /*0x1d54410*/
            goto LABEL_410; /*0x1d54414*/
          if ( v72 >= LODWORD(chapterInfos_26->max_length) ) /*0x1d54420*/
            goto LABEL_454; /*0x1d54420*/
          v177 = *((_QWORD *)&chapterInfos_26->obj.klass + n4); /*0x1d54424*/
          if ( !v177 ) /*0x1d54428*/
            goto LABEL_410; /*0x1d54428*/
          v178 = *(_QWORD *)(v177 + 96); /*0x1d5442c*/
          if ( !v178 ) /*0x1d54430*/
            goto LABEL_410; /*0x1d54430*/
          v179 = *(UnityEngine_Events_UnityEvent_o **)(v178 + 256); /*0x1d54434*/
          call_1 = (UnityEngine_Events_UnityAction_o *)sub_1B624E8(UnityEngine_Events_UnityAction_TypeInfo); /*0x1d54458*/
          UnityEngine_Events_UnityAction___ctor( /*0x1d54460*/
            call_1,
            (Il2CppObject *)object,
            Method_ChapterSelector___c__DisplayClass18_0__Start_b__2__,
            0);
          if ( !v179 ) /*0x1d54464*/
            goto LABEL_410; /*0x1d54464*/
          UnityEngine_Events_UnityEvent__AddListener(v179, call_1, 0); /*0x1d54474*/
        }
      }
      chapterPanel = (__int64)*p_chapterInfos; /*0x1d54478*/
      ++n4; /*0x1d5447c*/
      if ( !*p_chapterInfos ) /*0x1d54478*/
        goto LABEL_410; /*0x1d54480*/
    }
    chapterPanel_3 = this->fields.chapterPanel; /*0x1d544c0*/
    if ( !byte_446E25B ) /*0x1d544c4*/
    {
      sub_1B622A0(&UnityEngine_Vector2_TypeInfo, method); /*0x1d544d0*/
      byte_446E25B = 1; /*0x1d544d8*/
    }
    static_fields_1 = UnityEngine_Vector2_TypeInfo->static_fields; /*0x1d544f0*/
    x = static_fields_1->rightVector.fields.x; /*0x1d544f8*/
    y = static_fields_1->rightVector.fields.y; /*0x1d544f8*/
    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d544fc*/
      j_il2cpp_runtime_class_init_0(v65, v66, v67); /*0x1d54504*/
    Float = SaveManagement__LoadFloat((System_String_o *)"LastChapterSelectorPos", 0.0, 0); /*0x1d5451c*/
    if ( !chapterPanel_3 ) /*0x1d54528*/
      goto LABEL_410; /*0x1d54528*/
    value_1.fields.y = y * Float; /*0x1d5452c*/
    value_1.fields.x = x * Float; /*0x1d54530*/
    UnityEngine_RectTransform__set_anchoredPosition(chapterPanel_3, value_1, 0); /*0x1d5453c*/
    chapterPanel = (__int64)this->fields.chapterPanel; /*0x1d54540*/
    if ( !chapterPanel ) /*0x1d54544*/
      goto LABEL_410; /*0x1d54544*/
    LODWORD(this->fields._nowSliderPos) = *(_QWORD *)&UnityEngine_RectTransform__get_anchoredPosition( /*0x1d54550*/
                                                        (UnityEngine_RectTransform_o *)chapterPanel,
                                                        0);
    chapterPanel = SaveManagement__LoadInt((System_String_o *)"LastChapterSelectorIndex", 2, 0); /*0x1d54568*/
    chapterInfos = this->fields._chapterInfos; /*0x1d5456c*/
    this->fields._nowChapterIndex = chapterPanel; /*0x1d54570*/
    if ( !chapterInfos ) /*0x1d54574*/
      goto LABEL_410; /*0x1d54574*/
    max_length = chapterInfos->max_length; /*0x1d54578*/
    if ( (int)chapterPanel >= (int)max_length ) /*0x1d54580*/
    {
      chapterPanel = (unsigned int)(max_length - 1); /*0x1d54584*/
      this->fields._nowChapterIndex = chapterPanel; /*0x1d54588*/
    }
    if ( (unsigned int)chapterPanel >= (unsigned int)max_length ) /*0x1d54590*/
      goto LABEL_454; /*0x1d54590*/
    while ( 1 ) /*0x1d54598*/
    {
      chapterPanel = (__int64)chapterInfos->m_Items[(int)chapterPanel]; /*0x1d54598*/
      if ( !chapterPanel ) /*0x1d5459c*/
        goto LABEL_410; /*0x1d5459c*/
      chapterPanel = (__int64)UnityEngine_Component__get_gameObject((UnityEngine_Component_o *)chapterPanel, 0); /*0x1d545a4*/
      if ( !chapterPanel ) /*0x1d545a8*/
        goto LABEL_410; /*0x1d545a8*/
      chapterPanel = UnityEngine_GameObject__get_activeSelf((UnityEngine_GameObject_o *)chapterPanel, 0); /*0x1d545b0*/
      chapterInfos = *p_chapterInfos; /*0x1d545b4*/
      if ( (chapterPanel & 1) != 0 ) /*0x1d545b8*/
        break; /*0x1d545b8*/
      if ( !chapterInfos ) /*0x1d545bc*/
        goto LABEL_410; /*0x1d545bc*/
      max_length_1 = chapterInfos->max_length; /*0x1d545c4*/
      chapterPanel = (unsigned int)(this->fields._nowChapterIndex + 1); /*0x1d545c8*/
      if ( (int)chapterPanel >= (int)max_length_1 ) /*0x1d545d0*/
        goto LABEL_213; /*0x1d545d0*/
      this->fields._nowChapterIndex = chapterPanel; /*0x1d545d8*/
      if ( (unsigned int)chapterPanel >= (unsigned int)max_length_1 ) /*0x1d545dc*/
        goto LABEL_454; /*0x1d545dc*/
    }
    if ( !chapterInfos ) /*0x1d545e4*/
      goto LABEL_410; /*0x1d545e4*/
LABEL_213:
    nowChapterIndex = this->fields._nowChapterIndex; /*0x1d545e8*/
    while ( 1 ) /*0x1d545ec*/
    {
      if ( (unsigned int)nowChapterIndex >= LODWORD(chapterInfos->max_length) ) /*0x1d545f4*/
        goto LABEL_454; /*0x1d545f4*/
      chapterPanel = (__int64)chapterInfos->m_Items[nowChapterIndex]; /*0x1d545fc*/
      if ( !chapterPanel ) /*0x1d54600*/
        goto LABEL_410; /*0x1d54600*/
      chapterPanel = (__int64)UnityEngine_Component__get_gameObject((UnityEngine_Component_o *)chapterPanel, 0); /*0x1d54608*/
      if ( !chapterPanel ) /*0x1d5460c*/
        goto LABEL_410; /*0x1d5460c*/
      chapterPanel = UnityEngine_GameObject__get_activeSelf((UnityEngine_GameObject_o *)chapterPanel, 0); /*0x1d54614*/
      _nowChapterIndex = this->fields._nowChapterIndex; /*0x1d54618*/
      _nowChapterIndex_1 = _nowChapterIndex; /*0x1d5461c*/
      if ( (chapterPanel & 1) != 0 || _nowChapterIndex <= 0 ) /*0x1d54628*/
        break; /*0x1d54628*/
      chapterInfos = this->fields._chapterInfos; /*0x1d5462c*/
      nowChapterIndex = _nowChapterIndex_1 - 1; /*0x1d54630*/
      this->fields._nowChapterIndex = nowChapterIndex; /*0x1d54634*/
      if ( !chapterInfos ) /*0x1d54638*/
        goto LABEL_410; /*0x1d54638*/
    }
    chapterInfos_27 = *p_chapterInfos; /*0x1d54640*/
    if ( !*p_chapterInfos ) /*0x1d54644*/
      goto LABEL_410; /*0x1d54644*/
    if ( (unsigned int)_nowChapterIndex_1 >= LODWORD(chapterInfos_27->max_length) ) /*0x1d54650*/
      goto LABEL_454; /*0x1d54650*/