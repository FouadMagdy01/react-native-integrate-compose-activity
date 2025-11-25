import { launchNativeActivity } from 'react-native-integrate-compose-activity';
import React from 'react';
import { View, Button, Text, Alert, StyleSheet } from 'react-native';

const App = () => {
  const [result, setResult] = React.useState<string>('');

  const launchActivity = () => {
    launchNativeActivity(
      'Hello from React Native! This is Compose!',
      (resultData, error) => {
        if (error) {
          Alert.alert('Error', error);
          return;
        }

        setResult(resultData || 'No data returned');
        Alert.alert('Success', `Received: ${resultData}`);
      }
    );
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>React Native Turbo Module Demo</Text>
      <Button title="Launch Native Compose Activity" onPress={launchActivity} />
      {result ? (
        <View style={styles.resultContainer}>
          <Text style={styles.resultLabel}>Result from Native:</Text>
          <Text style={styles.resultText}>{result}</Text>
        </View>
      ) : null}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    padding: 20,
    backgroundColor: '#f5f5f5',
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 20,
    textAlign: 'center',
  },
  resultContainer: {
    marginTop: 20,
    padding: 15,
    backgroundColor: 'white',
    borderRadius: 8,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  resultLabel: {
    fontSize: 14,
    color: '#666',
    marginBottom: 5,
  },
  resultText: {
    fontSize: 16,
    color: '#333',
  },
});

export default App;
