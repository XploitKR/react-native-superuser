import { NativeModules } from 'react-native';

var RNSuperUser = NativeModules.RNSuperUser;

export default {
    isAvailable: function() {
        return RNSuperUser.isAvailable();
    },
    execute: function(command) {
        return RNSuperUser.execute(command);
    }
}