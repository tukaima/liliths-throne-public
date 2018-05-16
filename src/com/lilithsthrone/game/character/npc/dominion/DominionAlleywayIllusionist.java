package com.lilithsthrone.game.character.npc.dominion;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithsthrone.game.character.CharacterImportSetting;
import com.lilithsthrone.game.character.CharacterUtils;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.attributes.Attribute;
import com.lilithsthrone.game.character.fetishes.Fetish;
import com.lilithsthrone.game.character.fetishes.FetishDesire;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.character.gender.GenderPreference;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.persona.Name;
import com.lilithsthrone.game.character.race.RaceStage;
import com.lilithsthrone.game.character.race.RacialBody;
import com.lilithsthrone.game.character.race.Subspecies;
import com.lilithsthrone.game.combat.Spell;
import com.lilithsthrone.game.combat.SpellUpgrade;
import com.lilithsthrone.game.dialogue.DialogueNodeOld;
import com.lilithsthrone.game.dialogue.npcDialogue.dominion.AlleywayIllusionistDialogue;
import com.lilithsthrone.game.dialogue.npcDialogue.dominion.DominionSuccubusDialogue;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.CharacterInventory;
import com.lilithsthrone.game.inventory.clothing.AbstractClothingType;
import com.lilithsthrone.game.inventory.clothing.ClothingType;
import com.lilithsthrone.game.inventory.item.AbstractItem;
import com.lilithsthrone.game.inventory.item.ItemType;
import com.lilithsthrone.game.sex.Sex;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Vector2i;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.PlaceType;

/**
 * @since 0.2.58
 * @version 0.1.0
 * @author tukaima, Innoxia
 */
public class DominionAlleywayIllusionist extends NPC {

	private static final long serialVersionUID = 1L;
	
	public DominionAlleywayIllusionist() {
		this(false);
	}
	
	public DominionAlleywayIllusionist(boolean isImported) {
		super(null, "", 5, Gender.F_V_B_FEMALE, RacialBody.FOX_MORPH, RaceStage.GREATER,
				new CharacterInventory(10), WorldType.DOMINION, PlaceType.DOMINION_BACK_ALLEYS, false);

		if(!isImported) {
			
			this.setWorldLocation(Main.game.getPlayer().getWorldLocation());
			this.setLocation(new Vector2i(Main.game.getPlayer().getLocation().getX(), Main.game.getPlayer().getLocation().getY()));
			
			// BODY RANDOMISATION:
			addFetish(Fetish.FETISH_DEFLOWERING);
			addFetish(Fetish.FETISH_DOMINANT);
			CharacterUtils.addFetishes(this);
			
			Subspecies species = Subspecies.DEMON;
			Gender gender = GenderPreference.getGenderFromUserPreferences();
			
			if(gender.isFeminine()) {
				switch(Main.getProperties().subspeciesFeminineFurryPreferencesMap.get(species)) {
					case HUMAN:
						setBody(gender, RacialBody.DEMON, RaceStage.GREATER);
						break;
					case MINIMUM:
						setBodyFromPreferences(1, gender, species);
						break;
					case REDUCED:
						setBodyFromPreferences(2, gender, species);
						break;
					case NORMAL:
						setBodyFromPreferences(3, gender, species);
						break;
					case MAXIMUM:
						setBody(gender, species, RaceStage.GREATER);
						break;
				}
			} else {
				switch(Main.getProperties().subspeciesMasculineFurryPreferencesMap.get(species)) {
					case HUMAN:
						setBody(gender, RacialBody.DEMON, RaceStage.GREATER);
						break;
					case MINIMUM:
						setBodyFromPreferences(1, gender, species);
						break;
					case REDUCED:
						setBodyFromPreferences(2, gender, species);
						break;
					case NORMAL:
						setBodyFromPreferences(3, gender, species);
						break;
					case MAXIMUM:
						setBody(gender, species, RaceStage.GREATER);
						break;
				}
			}
			
			CharacterUtils.randomiseBody(this);
			
			this.setVaginaVirgin(false);
			this.setPenisVirgin(false);
			
			setLevel(Util.random.nextInt(3) + 4);
			
			setName(Name.getWeebName());
			this.setPlayerKnowsName(false);
			
			// Set random inventory & weapons:
			resetInventory();
			inventory.setMoney(50);
			CharacterUtils.generateItemsInInventory(this);
			
			// CLOTHING:
			
			this.equipClothing(true, false);
			
			CharacterUtils.applyMakeup(this, true);
			
			this.addSpell(Spell.ARCANE_AROUSAL);
			this.addSpell(Spell.TELEPATHIC_COMMUNICATION);
			this.addSpellUpgrade(SpellUpgrade.TELEPATHIC_COMMUNICATION_1);
			
			setMana(getAttributeValue(Attribute.MANA_MAXIMUM));
			setHealth(getAttributeValue(Attribute.HEALTH_MAXIMUM));
		}

		this.setEnslavementDialogue(DominionSuccubusDialogue.ENSLAVEMENT_DIALOGUE);
	}
	
	private void setBodyFromPreferences(int i, Gender gender, Subspecies species) {
		int choice = Util.random.nextInt(i)+1;
		RaceStage raceStage = RaceStage.PARTIAL;
		
		if (choice == 1) {
			raceStage = RaceStage.PARTIAL;
		} else if (choice == 2) {
			raceStage = RaceStage.LESSER;
		} else {
			raceStage = RaceStage.GREATER;
		}
		
		setBody(gender, species, raceStage);
	}
	
	@Override
	public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
		loadNPCVariablesFromXML(this, null, parentElement, doc, settings);
		if(this.getFetishDesire(Fetish.FETISH_NON_CON_DOM)==FetishDesire.ONE_DISLIKE || this.getFetishDesire(Fetish.FETISH_NON_CON_DOM)==FetishDesire.ZERO_HATE) {
			this.setFetishDesire(Fetish.FETISH_NON_CON_DOM, FetishDesire.TWO_NEUTRAL);
		}
	}
	
	@Override
	public boolean isUnique() {
		return false;
	}

	@Override
	public String getDescription() {
		return UtilText.parse(this,
				"A youko wandering the streets of Dominion, assaulting and enchanting those who enter [npc.his] domain.");
	}
	
	@Override
	public void equipClothing(boolean replaceUnsuitableClothing, boolean onlyAddCoreClothing) {
		CharacterUtils.equipClothing(this, replaceUnsuitableClothing, onlyAddCoreClothing);
	}
	
	@Override
	public void endSex(boolean applyEffects) {
		if (applyEffects) {
			if(!isSlave()) {
				if(this.getGender()!=this.getGenderIdentity() && !this.isPregnant()) {
					this.setPendingTransformationToGenderIdentity(true);
				}
				setPendingClothingDressing(true);
			}
		}
	}

	@Override
	public boolean isClothingStealable() {
		return true;
	}
	
	@Override
	public boolean isAbleToBeImpregnated() {
		return true;
	}
	
	@Override
	public void changeFurryLevel(){
	}
	
	@Override
	public DialogueNodeOld getEncounterDialogue() {
		return AlleywayIllusionistDialogue.ALLEY_FOX_ATTACK;
	}

	// Combat:

	@Override
	public String getCombatDescription() {
		return UtilText.parse(this,
				"A youko wandering the streets of Dominion, assaulting and enchanting those who enter [npc.his] domain.");
	}

	public String getItemUseEffects(AbstractItem item, GameCharacter user, GameCharacter target){
		// Player is using an item:
		if(user.isPlayer()){
			// Player uses item on themselves:
			if(target.isPlayer()){
				return Main.game.getPlayer().useItem(item, target, false);
				
			// Player uses item on NPC:
			}else{
				if(item.getItemType().equals(ItemType.PROMISCUITY_PILL)) {
					
						Main.game.getPlayer().useItem(item, target, false);
						if(Sex.isDom(Main.game.getPlayer())) {
							return "<p>"
									+ "Holding out a 'Promiscuity pill' to [npc.name], you tell [npc.her] to swallow it so that you don't have to worry about any unexpected pregnancies."
									+ " Letting out a reluctant sigh, [npc.she] nevertheless takes the pill out of your hand, and, popping it out of its wrapping, [npc.she] whines at you,"
									+ " [npc.speech(Fine! I kinda like the taste of these things anyway...)]"
									+ "</p>";
						} else {
							return "<p>"
									+ "Holding out a 'Promiscuity pill' to [npc.name], you ask [npc.her] to swallow it so that you don't have to worry about any unexpected pregnancies."
									+ " Letting out an annoyed sigh, [npc.she] nevertheless takes the pill out of your hand, and, popping it out of its wrapping, [npc.she] growls at you,"
									+ " [npc.speech(Fine! I don't care either way, but I kinda like the taste of these things...)]"
									+ "</p>";
						}
						
				} else if(item.getItemType().equals(ItemType.VIXENS_VIRILITY)) {

						Main.game.getPlayer().useItem(item, target, false);
						if(Sex.isDom(Main.game.getPlayer())) {
							return "<p>"
									+ "Holding out a 'Vixen's Virility' pill to [npc.name], you tell [npc.her] to swallow it."
									+ " Letting out a reluctant sigh, [npc.she] nevertheless takes the pill out of your hand, and, popping it out of its wrapping, [npc.she] whines at you,"
									+ " [npc.speech(Fine! I kinda like the taste of these things anyway...)]"
									+ "</p>";
						} else {
							return "<p>"
									+ "Holding out a 'Vixen's Virility' pill to [npc.name], you ask [npc.her] to swallow it."
									+ " Letting out an annoyed sigh, [npc.she] nevertheless takes the pill out of your hand, and, popping it out of its wrapping, [npc.she] growls at you,"
									+ " [npc.speech(Fine! I don't care either way, but I kinda like the taste of these things...)]"
									+ "</p>";
						}
						
				} else if(item.getItemType().equals(ItemType.POTION) || item.getItemType().equals(ItemType.ELIXIR)) {
					
						if(Sex.isDom(Main.game.getPlayer())) {
							Main.game.getPlayer().removeItem(item);
							return "<p>"
										+ "Taking your "+item.getName()+" out from your inventory, you hold it out to [npc.name]."
										+ " Seeing what you're offering [npc.herHim], [npc.she] lets out a little laugh, "
										+ " [npc.speech(Hah! Don't you know youkos can't be transfo~Mrph!~)]"
									+ "</p>"
										+ "Not liking the start of [npc.her] response, you quickly remove the bottle's stopper, and rather unceremoniously shove the neck down [npc.her] throat."
										+ " You pinch [npc.her] nose and hold [npc.herHim] still, forcing [npc.herHim] to down all of the liquid before finally letting [npc.her] go."
										+ " [npc.She] coughs and splutters for a moment, before letting out an annoyed cry as [npc.she] wipes the liquid from [npc.her] mouth,"
										+ " [npc.speech(W-what did I just say? Youkos can't be transformed like that! But the taste is kinda nice I suppose...)]"
									+ "</p>";
						} else {
							return "<p>"
										+ "You try to give [npc.name] your "+item.getName()+", but [npc.she] takes one look at it and laughs,"
										+ " [npc.speech(Hah! Nice try, but do you really expect me to drink some random potion?!)]</br>"
										+ "You reluctantly put the "+item.getName()+" back in your inventory, disappointed that [npc.she]'s not interested."
									+ "</p>";
						}
						
				} else if(item.getItemType().equals(ItemType.FETISH_UNREFINED) || item.getItemType().equals(ItemType.FETISH_REFINED)) {
					
					if(Sex.isDom(Main.game.getPlayer())) {
						Main.game.getPlayer().removeItem(item);
						return "<p>"
									+ "Taking your "+item.getName()+" out from your inventory, you hold it out to [npc.name]."
									+ " Seeing what you're offering [npc.herHim], [npc.she] lets out a little laugh, "
									+ " [npc.speech(Hah! Don't you know youkos can't be transfo~Mrph!~)]"
								+ "</p>"
									+ "Not liking the start of [npc.her] response, you quickly remove the bottle's stopper, and rather unceremoniously shove the neck down [npc.her] throat."
									+ " You pinch [npc.her] nose and hold [npc.herHim] still, forcing [npc.herHim] to down all of the liquid before finally letting [npc.her] go."
									+ " [npc.She] coughs and splutters for a moment, before letting out a lewd little cry as [npc.she] wipes the liquid from [npc.her] mouth,"
									+ " [npc.speech(W-Wait! That was a fetish transformative?! I feel... hot...)]"
								+ "</p>"
								+ Main.game.getPlayer().useItem(item, target, false);
					} else {
						return "<p>"
									+ "You try to give [npc.name] your "+item.getName()+", but [npc.she] takes one look at it and laughs,"
									+ " [npc.speech(Hah! Nice try, but do you really expect me to drink some random potion?!)]</br>"
									+ "You reluctantly put the "+item.getName()+" back in your inventory, disappointed that [npc.she]'s not interested."
								+ "</p>";
					}
					
				} else if(item.getItemType().equals(ItemType.EGGPLANT)) {
						if(Sex.isDom(Main.game.getPlayer())) {
							return "<p>"
										+ "Taking the eggplant from your inventory, you hold it out to [npc.name]."
										+ " Seeing what you're offering [npc.herHim], [npc.she] shifts about uncomfortably, "
										+ " [npc.speech(W-What are you going to do with th-~Mrph!~)]"
									+ "</p>"
									+ "<p>"
										+ "Not liking the start of [npc.her] response, you quickly shove the eggplant into [npc.her] mouth, grinning as you force [npc.herHim] to eat the purple fruit..."
									+ "</p>"
									+Main.game.getPlayer().useItem(item, target, false, true);
						} else {
							return "<p>"
										+ "You try to give [npc.name] your "+item.getName()+", but [npc.she] takes one look at it and laughs,"
										+ " [npc.speech(Hah! Did you really think I was going to eat that?!)]</br>"
										+ "You reluctantly put the "+item.getName()+" back in your inventory, disappointed that [npc.she]'s not interested."
									+ "</p>";
						}
						
				} else {
					return "<p>"
								+ "You try to give [npc.name] "+item.getItemType().getDeterminer()+" "+item.getName()+", but [npc.she] refuses to take it. You put the "+item.getName()+" back in your inventory."
							+ "</p>";
				}
			}
			
		// NPC is using an item:
		}else{
			return Sex.getActivePartner().useItem(item, target, false);
		}
	}

	@Override
	public String getMainAttackDescription(boolean isHit) {
		if(this.isFeminine()) {
			return UtilText.parse(this,
					UtilText.returnStringAtRandom(
							"[npc.Name] looks annoyed that you're trying to put up a fight, and leaps forwards to deliver a stinging slap across your face.",
							"With an angry little click of her tongue, [npc.Name] slaps you across the face.",
							"With a frustrated whine, [npc.Name] kicks out at your shins."));
		} else {
			return UtilText.parse(this,
					UtilText.returnStringAtRandom(
							"[npc.Name] looks annoyed that you're trying to put up a fight, and leaps forwards to deliver a solid punch to your [pc.arm].",
							"With an angry shout, [npc.Name] darts forwards and punches you right in the chest!",
							"With a frustrated cry, [npc.Name] kicks out at your shins."));
		}
	}

	@Override
	public Response endCombat(boolean applyEffects, boolean victory) {
		if (victory) {
			return new Response("", "", DominionSuccubusDialogue.AFTER_COMBAT_VICTORY);
		} else {
			return new Response ("", "", DominionSuccubusDialogue.AFTER_COMBAT_DEFEAT);
		}
	}
	
	
	// ****************** Sex & Dirty talk: ***************************
	
//	@Override
//	public SexType getForeplayPreference() {
//		if(Main.game.getPlayer().hasVagina() && Main.game.getPlayer().isAbleToAccessCoverableArea(CoverableArea.VAGINA, true)) {
//			return new SexType(SexParticipantType.PITCHER, PenetrationType.FINGER, OrificeType.VAGINA);
//			
//		} else if(Main.game.getPlayer().isAbleToAccessCoverableArea(CoverableArea.ANUS, true)) {
//			return new SexType(SexParticipantType.PITCHER, PenetrationType.FINGER, OrificeType.ANUS);
//		}
//		
//		return foreplayPreference;
//	}
//	
//	@Override
//	public SexType getMainSexPreference() {
//		if(Main.game.getPlayer().hasVagina() && Main.game.getPlayer().isAbleToAccessCoverableArea(CoverableArea.VAGINA, true)) {
//			return new SexType(SexParticipantType.PITCHER, PenetrationType.PENIS, OrificeType.VAGINA);
//			
//		} else if(Main.game.getPlayer().isAbleToAccessCoverableArea(CoverableArea.ANUS, true)) {
//			return new SexType(SexParticipantType.PITCHER, PenetrationType.PENIS, OrificeType.ANUS);
//		}
//		
//		return mainSexPreference;
//	}
//	
//	public Set<SexPositionSlot> getSexPositionPreferences() {
//		sexPositionPreferences.clear();
//		
//		if(Sex.isInForeplay()) {
//			if(Main.game.getPlayer().hasVagina() && Main.game.getPlayer().isAbleToAccessCoverableArea(CoverableArea.VAGINA, true)) {
//				sexPositionPreferences.add(SexPositionSlot.BACK_TO_WALL_FACING_TARGET);
//				sexPositionPreferences.add(SexPositionSlot.DOGGY_BEHIND);
//				sexPositionPreferences.add(SexPositionSlot.FACE_TO_WALL_FACING_TARGET);
//				
//			} else if(Main.game.getPlayer().isAbleToAccessCoverableArea(CoverableArea.ANUS, true)) {
//				sexPositionPreferences.add(SexPositionSlot.DOGGY_BEHIND);
//				sexPositionPreferences.add(SexPositionSlot.FACE_TO_WALL_FACING_TARGET);
//				
//			} else {
//				return super.getSexPositionPreferences();
//			}
//			
//		} else {
//			if(Main.game.getPlayer().hasVagina() && Main.game.getPlayer().isAbleToAccessCoverableArea(CoverableArea.VAGINA, true)) {
//				sexPositionPreferences.add(SexPositionSlot.BACK_TO_WALL_FACING_TARGET);
//				sexPositionPreferences.add(SexPositionSlot.DOGGY_BEHIND);
//				sexPositionPreferences.add(SexPositionSlot.FACE_TO_WALL_FACING_TARGET);
//				
//			} else if(Main.game.getPlayer().isAbleToAccessCoverableArea(CoverableArea.ANUS, true)) {
//				sexPositionPreferences.add(SexPositionSlot.DOGGY_BEHIND);
//				sexPositionPreferences.add(SexPositionSlot.FACE_TO_WALL_FACING_TARGET);
//				
//			} else {
//				return super.getSexPositionPreferences();
//			}
//		}
//		
//		return sexPositionPreferences;
//	}
	
	@Override
	public String getCondomEquipEffects(GameCharacter equipper, GameCharacter target, boolean rough) {
		if(Main.game.isInSex()) {
			if((Sex.isDom(Main.game.getPlayer()) || Sex.isSubHasEqualControl()) && !target.isPlayer()) {
				return "<p>"
							+ "Holding out a condom to [npc.name], you force [npc.herHim] to take it and put it on."
							+ " Quickly ripping it out of its little foil wrapper, [npc.she] rolls it down the length of [npc.her] [npc.cock+] as [npc.she] whines at you,"
							+ " [npc.speech(Do I really have to? It feels so much better without one...)]"
						+ "</p>";
			} else if (!target.isPlayer()){
				target.unequipClothingIntoVoid(target.getClothingInSlot(ClothingType.PENIS_CONDOM.getSlot()), true, equipper);
				return "<p>"
							+ "You pull out a condom and try to give it to the horny [npc.race], but [npc.she] simply laughs in your face before grabbing the little foil packet and tearing it in two."
							+ " Mocking your attempt at trying to get her to wear a rubber, [npc.she] sneers,"
							+ " [npc.speech(Hah! I don't think so!)]"
						+ "</p>";
			}
		}
		return AbstractClothingType.getEquipDescriptions(target, equipper, rough,
				"You tear open the packet and roll the condom down the length of your [pc.penis].",
				"You tear open the packet and roll the condom down the length of [npc.name]'s [npc.penis].",
				"You tear open the packet and forcefully roll the condom down the length [npc.name]'s [npc.penis].",
				"[npc.Name] tears open the packet and rolls the condom down the length of [npc.her] [npc.penis].",
				"[npc.Name] tears open the packet and rolls the condom down the length of your [pc.penis].",
				"[npc.Name] tears open the packet and forcefully rolls the condom down the length of your [pc.penis].", null, null);
	}
}
