package github.zmilla93.gui.donate

class Supporters {

    companion object {

        val patrons = listOf(
            PatreonSupporter("Alin Șerban", PatreonTier.ONE),
            PatreonSupporter("Cani Glauss", PatreonTier.THREE, false),
            PatreonSupporter("Casey Schaffer", PatreonTier.FIVE),
            PatreonSupporter("ciXPence", PatreonTier.ONE, false),
            PatreonSupporter("Deific", PatreonTier.THREE),
            PatreonSupporter("G4MERLIN", PatreonTier.ONE, false),
            PatreonSupporter("Jason Ballew", PatreonTier.THREE),
            PatreonSupporter("Reynbow", PatreonTier.ONE),
            PatreonSupporter("toughguy247", PatreonTier.ONE, false),
        )

        val paypal = listOf(
            PayPalSupporter("Alex Ahnon Hansen", PayPalTier.TWENTY),
            PayPalSupporter("Anthony Rampone", PayPalTier.ONE),
            PayPalSupporter("Anton Oparienko", PayPalTier.ONE),
            PayPalSupporter("casualbox", PayPalTier.TEN),
            PayPalSupporter("David Stewart", PayPalTier.TWENTY),
            PayPalSupporter("Ioan Petculescu", PayPalTier.ONE),
            PayPalSupporter("Jason Foster", PayPalTier.TWENTY),
            PayPalSupporter("Jordan Newman", PayPalTier.TWENTY),
            PayPalSupporter("Jean-Yves Morvan", PayPalTier.FIVE),
            PayPalSupporter("Lennart Marmeier", PayPalTier.FIVE),
            PayPalSupporter("Midwest Invasion", PayPalTier.FIVE),
            PayPalSupporter("Teresa Wise", PayPalTier.FIVE),
            PayPalSupporter("Travis Thompson", PayPalTier.ONE),
        )
    }

    class PatreonSupporter(val name: String, val tier: PatreonTier, val active: Boolean = true)
    class PayPalSupporter(val name: String, val tier: PayPalTier)

}