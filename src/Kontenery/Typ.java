package Kontenery;

public enum Typ {

    Podstawowy(0){
        @Override
        public String toString() {
            return "podstawowy";
        }
    },
    Ciezki(1){
        @Override
        public String toString() {
            return "ciężki";
        }
    },
    Wybuch(2){
        @Override
        public String toString() {
            return "ciężki, z materiałami wybuchowymi";
        }

    },
    Chlodniczy(3){
        @Override
        public String toString() {
            return "ciężki, chłodniczy";
        }
    },
    Ciekly(4){
        @Override
        public String toString() {
            return "na materiały ciekłe";
        }
    },
    ToksSypki(5){
        @Override
        public String toString() {
            return "ciężki, na materiały toksyczne sypkie";
        }
    },

    ToksCiekly(6){
        @Override
        public String toString() {
            return "ciężki, na materiały toksyczne ciekłe";
        }
    };

    private final int value;

    Typ(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

