import IntegrateComposeActivity from './NativeIntegrateComposeActivity';

export function launchNativeActivity(
  data: string,
  callback: (result: string, error: string | null) => void
): void {
  return IntegrateComposeActivity.launchNativeActivity(data, callback);
}
