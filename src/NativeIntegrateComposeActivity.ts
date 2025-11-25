import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  launchNativeActivity(
    data: string,
    callback: (result: string, error: string | null) => void
  ): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>(
  'IntegrateComposeActivity'
);
