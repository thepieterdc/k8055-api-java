package be.thepieterdc.k8055.input;

import be.thepieterdc.k8055.K8055;
import be.thepieterdc.k8055.exceptions.ConnectionStatusException;
import be.thepieterdc.k8055.helpers.IO;

public class DigitalInput extends Input<DigitalInput.DigitalInputs,Boolean> {

    public enum DigitalInputs implements IO.IOInterface {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5);

        private final int channel;

        DigitalInputs(int chan) {
            this.channel = chan;
        }

        @Override
        public int channel() {
            return this.channel;
        }

        public static DigitalInputs fromChannel(int c) {
            for(DigitalInputs dis : DigitalInputs.values()) {
                if(dis.channel == c) {
                    return dis;
                }
            }
            throw new IllegalArgumentException("Channel must be in range [1-5].");
        }
    }

    public DigitalInput(K8055 k8055, DigitalInputs digitalInputs) {
        super(k8055, Signal.DIGITAL, digitalInputs);
    }

    @Override
    public Boolean value() {
        if(!this.k8055.connected()) {
            throw ConnectionStatusException.connectionRequired();
        }
        return this.k8055.board().ReadDigitalChannel(this.ioInterface.channel);
    }
}