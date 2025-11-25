# react-native-integrate-compose-activity

Integrate jetpack compose  activity into react native apps

## Installation


```sh
npm install react-native-integrate-compose-activity
```


## Usage

```js
import { launchNativeActivity } from 'react-native-integrate-compose-activity';

// Launch a native Jetpack Compose activity
launchNativeActivity(
  'Hello from React Native!',
  (result, error) => {
    if (error) {
      console.error('Error:', error);
      return;
    }
    console.log('Result from native:', result);
  }
);
```


## Contributing

- [Development workflow](CONTRIBUTING.md#development-workflow)
- [Sending a pull request](CONTRIBUTING.md#sending-a-pull-request)
- [Code of conduct](CODE_OF_CONDUCT.md)

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
