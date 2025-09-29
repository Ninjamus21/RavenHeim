package RavenHeimGame.CharacterSheet;

public class CharactorSheetBluePrint {
    public static class BaseStats{
        private int strength;
        private int agility;
        private int intelligence;
        private int will;
        private int vitality;
        private int mana;
        private int luck;
        private int strategy;
        private int toughness;
        private int dexterity;
        private int magic;

        public BaseStats(int strength, int agility, int intelligence, int will,
                     int vitality, int mana, int luck, int strategy,
                     int toughness, int dexterity, int magic) {
            this.strength = strength;
            this.agility = agility;
            this.intelligence = intelligence;
            this.will = will;
            this.vitality = vitality;
            this.mana = mana;
            this.luck = luck;
            this.strategy = strategy;
            this.toughness = toughness;
            this.dexterity = dexterity;
            this.magic = magic;
        }
        // Derived stats
        public int getHitPoints(){
            return (int)(vitality * 2 + luck * 1.0 - magic * 1.1);
        }
        public int getManaPoints(){
            return (int)(mana * 5 + intelligence * 2 + will * 1.5 + strategy * 1.1);
        }
        public int getPhysicalAttackPower(){
            return (int)(strength * 1.5 + agility * 1.2 + vitality * 1.3 + dexterity * 1.3);
        }
        public int getPhysicalDefence(){
            return (int)(toughness * 1.6 + strength * 1.1 + vitality * 1.4 + dexterity * 1.1);
        }
        public int getDexterity(){
            return (int)(agility * 1.5 + dexterity * 2);
        }
        public int getMagicAttack(){
            return (int)(magic * 10 + will * 1.5 + intelligence * 1.5 + strategy * 1.3 + luck * 1.2);
        }
        public int getMagicDefence(){
            return (int)(will * 2 + mana * 100 + toughness * 1.2 + vitality * 1.2 + luck * 1.1);
        }
        public int getTotalResistance(){
            return (int)(toughness * 1.5 + strength * 1.4 + will * 1.4 + vitality * 1.3 + magic * 1.1 + mana * 1.1);
        }
        public int getMoveSpeed(){
            return (int)(agility * 1.5 + dexterity * 1.4 - vitality * 1.2);
        }
        public int getStrategicMind() {
            return (int) (strategy * 3 + intelligence * 1.5 + luck * 1.6);
        }
    }
}
