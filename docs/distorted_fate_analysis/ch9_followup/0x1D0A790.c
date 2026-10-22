System_String_o *Phigros2_Chapter9_ExoplanetaryMirageUnlock__GetPendingHiddenThirdSongId(
        System_Collections_Generic_IReadOnlyList_ChallengeSongItem__o *items,
        const MethodInfo *method)
{
  System_Collections_Generic_IReadOnlyList_ChallengeSongItem__o *items_1; // x19
  const MethodInfo *method_1; // x1

  items_1 = items; /*0x1d0a7a0*/
  if ( (byte_446E6AD & 1) == 0 ) /*0x1d0a7a4*/
  {
    items = (System_Collections_Generic_IReadOnlyList_ChallengeSongItem__o *)sub_1B622A0(&"ExoplanetaryMirage.かめりあ.0", method); /*0x1d0a7b0*/
    byte_446E6AD = 1; /*0x1d0a7b8*/
  }
  if ( !Phigros2_Chapter9_ExoplanetaryMirageUnlock__IsProcessActive((const MethodInfo *)items) ) /*0x1d0a7c0*/
    return 0; /*0x1d0a7e4*/
  if ( Phigros2_Chapter9_ExoplanetaryMirageUnlock__IsValidUnlockSelection(items_1, method_1) ) /*0x1d0a7d0*/
    return (System_String_o *)"ExoplanetaryMirage.かめりあ.0"; /*0x1d0a7dc*/
  return 0; /*0x1d0a7f0*/
}
