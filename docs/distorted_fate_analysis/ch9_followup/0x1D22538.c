void Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl___cctor(const MethodInfo *method)
{
  __int64 v1; // x1
  __int64 v2; // x1
  __int64 v3; // x1
  __int64 v4; // x1
  __int64 v5; // x1
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 v8; // x1
  System_Collections_Generic_Dictionary_object__object__o *C9HiddenSongUnlockKeys; // x19
  __int64 v10; // x0
  __int64 v11; // x1

  if ( (byte_446E7A4 & 1) == 0 ) /*0x1d2255c*/
  {
    sub_1B622A0(&Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_TypeInfo, v1); /*0x1d22568*/
    sub_1B622A0(&Method_System_Collections_Generic_Dictionary_string__string__Add__, v2); /*0x1d22574*/
    sub_1B622A0(&Method_System_Collections_Generic_Dictionary_string__string___ctor__, v3); /*0x1d22580*/
    sub_1B622A0(&System_Collections_Generic_Dictionary_string__string__TypeInfo, v4); /*0x1d2258c*/
    sub_1B622A0(&"C9ExoplanetaryMirageUnlocked", v5); /*0x1d22598*/
    sub_1B622A0(&"ExoplanetaryMirage.かめりあ.0", v6); /*0x1d225a4*/
    sub_1B622A0(&"C9EntranceToTheChaosUnlocked", v7); /*0x1d225b0*/
    sub_1B622A0(&"EntrancetotheChaos.打打だいずvssiromaru.0", v8); /*0x1d225bc*/
    byte_446E7A4 = 1; /*0x1d225c4*/
  }
  C9HiddenSongUnlockKeys = (System_Collections_Generic_Dictionary_object__object__o *)sub_1B624E8(System_Collections_Generic_Dictionary_string__string__TypeInfo); /*0x1d225d4*/
  System_Collections_Generic_Dictionary_object__object____ctor( /*0x1d225d8*/
    C9HiddenSongUnlockKeys,
    (const MethodInfo_2A04640 *)Method_System_Collections_Generic_Dictionary_string__string___ctor__);
  if ( !C9HiddenSongUnlockKeys ) /*0x1d225dc*/
    sub_1B624F8(v10, v11); /*0x1d22654*/
  System_Collections_Generic_Dictionary_object__object___Add( /*0x1d22620*/
    C9HiddenSongUnlockKeys,
    (Il2CppObject *)"ExoplanetaryMirage.かめりあ.0",
    (Il2CppObject *)"C9ExoplanetaryMirageUnlocked",
    (const MethodInfo_2A04F94 *)Method_System_Collections_Generic_Dictionary_string__string__Add__);
  System_Collections_Generic_Dictionary_object__object___Add( /*0x1d22634*/
    C9HiddenSongUnlockKeys,
    (Il2CppObject *)"EntrancetotheChaos.打打だいずvssiromaru.0",
    (Il2CppObject *)"C9EntranceToTheChaosUnlocked",
    (const MethodInfo_2A04F94 *)Method_System_Collections_Generic_Dictionary_string__string__Add__);
  Phigros2_ChallengeMode_Scripts_ChallengeModeOverControl_TypeInfo->static_fields->C9HiddenSongUnlockKeys = (struct System_Collections_Generic_Dictionary_string__string__o *)C9HiddenSongUnlockKeys; /*0x1d22644*/
}
