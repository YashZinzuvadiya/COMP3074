import React, { useState } from 'react';
import {
  StyleSheet,
  Text,
  View,
  Pressable
} from 'react-native';

export default function App() {
  const [greeted, setGreeted] = useState(false);

  return (
    <View style={styles.container}>

      <View style={styles.card}>

        <Text style={styles.label}>
          COMP3074 - Mobile App Development
        </Text>

        <Text style={styles.name}>
          Yash Zinzuvadiya
        </Text>

        <View style={styles.divider} />

        <Text style={styles.studentId}>
          Student ID: 101475983
        </Text>

        <Pressable
          style={({ pressed }) => [
            styles.button,
            pressed && styles.buttonPressed,
          ]}
          onPress={() => setGreeted(!greeted)}
        >
          <Text style={styles.buttonText}>
            {greeted ? 'Hello, React Native!' : 'Say Hello'}
          </Text>
        </Pressable>

      </View>

    </View>
  );
}

const styles = StyleSheet.create({

  container: {
    flex: 1,
    backgroundColor: '#2B2D42',
    justifyContent: 'flex-start',
    alignItems: 'center',
    paddingTop: 120,
    paddingHorizontal: 24,
  },

  card: {
    width: '90%',
    paddingVertical: 32,
    paddingHorizontal: 24,
    backgroundColor: '#EDF2F4',
    borderRadius: 20,
    alignItems: 'flex-start',
    borderLeftWidth: 8,
    borderLeftColor: '#EF233C',
  },

  label: {
    fontSize: 13,
    color: '#8D99AE',
    letterSpacing: 1,
    textTransform: 'uppercase',
  },

  name: {
    fontSize: 36,
    fontWeight: 'bold',
    color: '#2B2D42',
    marginTop: 10,
  },

  divider: {
    width: 60,
    height: 4,
    backgroundColor: '#EF233C',
    borderRadius: 2,
    marginVertical: 16,
  },

  studentId: {
    fontSize: 18,
    color: '#D90429',
    fontWeight: '600',
  },

  button: {
    marginTop: 28,
    alignSelf: 'stretch',
    paddingVertical: 14,
    backgroundColor: '#2B2D42',
    borderRadius: 12,
    alignItems: 'center',
  },

  buttonPressed: {
    opacity: 0.75,
  },

  buttonText: {
    fontSize: 16,
    color: '#EDF2F4',
    fontWeight: '600',
  },

});
