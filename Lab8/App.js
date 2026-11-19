import React, { useEffect, useState } from 'react';
import {
  ActivityIndicator,
  FlatList,
  Platform,
  Pressable,
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  TextInput,
  View,
} from 'react-native';

const USERS_URL = 'https://jsonplaceholder.typicode.com/users';

// Returns up to two initials from a full name, e.g. "Leanne Graham" -> "LG".
function getInitials(name) {
  return name
    .split(' ')
    .filter(Boolean)
    .slice(0, 2)
    .map((part) => part[0].toUpperCase())
    .join('');
}

function Avatar({ name, size = 44 }) {
  return (
    <View
      style={[
        styles.avatar,
        { width: size, height: size, borderRadius: size / 2 },
      ]}
    >
      <Text style={[styles.avatarText, { fontSize: size * 0.4 }]}>
        {getInitials(name)}
      </Text>
    </View>
  );
}

// One selectable row in the user list.
function UserItem({ user, onPress }) {
  return (
    <Pressable
      style={({ pressed }) => [styles.userItem, pressed && styles.pressed]}
      onPress={onPress}
    >
      <Avatar name={user.name} />
      <View style={styles.userInfo}>
        <Text style={styles.userName}>{user.name}</Text>
        <Text style={styles.userEmail}>{user.email}</Text>
      </View>
      <Text style={styles.chevron}>›</Text>
    </Pressable>
  );
}

// A label/value pair on the details screen.
function DetailRow({ label, value, highlight }) {
  return (
    <View>
      <Text style={styles.detailLabel}>{label}</Text>
      <Text style={[styles.detailValue, highlight && styles.linkText]}>
        {value}
      </Text>
    </View>
  );
}

export default function App() {
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [search, setSearch] = useState('');

  // Retrieves the users from the JSONPlaceholder REST API.
  const loadUsers = async () => {
    try {
      setLoading(true);
      setError(null);

      const response = await fetch(USERS_URL);

      if (!response.ok) {
        throw new Error(`HTTP error: ${response.status}`);
      }

      const data = await response.json();
      setUsers(data);
    } catch (err) {
      setError('Unable to load users.');
    } finally {
      setLoading(false);
    }
  };

  // Run the request once, after the first render.
  useEffect(() => {
    loadUsers();
  }, []);

  // Loading state
  if (loading) {
    return (
      <View style={styles.centered}>
        <ActivityIndicator size="large" color="#1D4ED8" />
        <Text style={styles.message}>Loading users...</Text>
      </View>
    );
  }

  // Error state with a Retry button
  if (error) {
    return (
      <View style={styles.centered}>
        <Text style={styles.errorText}>{error}</Text>
        <Pressable style={styles.primaryButton} onPress={loadUsers}>
          <Text style={styles.primaryButtonText}>Retry</Text>
        </Pressable>
      </View>
    );
  }

  // List view: shown when no user is selected
  if (selectedUser === null) {
    const filteredUsers = users.filter((user) =>
      user.name.toLowerCase().includes(search.trim().toLowerCase())
    );

    return (
      <SafeAreaView style={styles.container}>
        <View style={styles.header}>
          <Text style={styles.title}>Users</Text>
          <Pressable style={styles.primaryButton} onPress={loadUsers}>
            <Text style={styles.primaryButtonText}>Refresh</Text>
          </Pressable>
        </View>

        <TextInput
          style={styles.searchInput}
          placeholder="Search by name..."
          value={search}
          onChangeText={setSearch}
          autoCorrect={false}
          clearButtonMode="while-editing"
        />

        <FlatList
          data={filteredUsers}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => (
            <UserItem user={item} onPress={() => setSelectedUser(item)} />
          )}
          ListEmptyComponent={
            <Text style={styles.message}>No users match "{search}".</Text>
          }
        />
      </SafeAreaView>
    );
  }

  // Details view: shown when a user is selected
  const { address, company } = selectedUser;

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView contentContainerStyle={styles.details}>
        <Pressable
          style={styles.backButton}
          onPress={() => setSelectedUser(null)}
        >
          <Text style={styles.backButtonText}>‹ Back to Users</Text>
        </Pressable>

        <Text style={styles.title}>User Details</Text>

        <View style={styles.profileHeader}>
          <Avatar name={selectedUser.name} size={72} />
          <View style={styles.userInfo}>
            <Text style={styles.profileName}>{selectedUser.name}</Text>
            <Text style={styles.profileUsername}>
              @{selectedUser.username}
            </Text>
          </View>
        </View>

        <View style={styles.card}>
          <DetailRow label="ID" value={selectedUser.id} />
          <DetailRow label="Name" value={selectedUser.name} />
          <DetailRow label="Username" value={selectedUser.username} />
          <DetailRow label="Email" value={selectedUser.email} highlight />
          <DetailRow label="Phone" value={selectedUser.phone} highlight />
          <DetailRow label="Website" value={selectedUser.website} highlight />
        </View>

        <Text style={styles.sectionTitle}>Address</Text>
        <View style={styles.card}>
          <DetailRow label="Street" value={address.street} />
          <DetailRow label="Suite" value={address.suite} />
          <DetailRow label="City" value={address.city} />
          <DetailRow label="Zip Code" value={address.zipcode} />
        </View>

        <Text style={styles.sectionTitle}>Coordinates</Text>
        <View style={[styles.card, styles.geoCard]}>
          <View style={styles.geoItem}>
            <Text style={styles.detailLabel}>Latitude</Text>
            <Text style={styles.geoValue}>{address.geo.lat}</Text>
          </View>
          <View style={styles.geoItem}>
            <Text style={styles.detailLabel}>Longitude</Text>
            <Text style={styles.geoValue}>{address.geo.lng}</Text>
          </View>
        </View>

        <Text style={styles.sectionTitle}>Company</Text>
        <View style={styles.card}>
          <DetailRow label="Company Name" value={company.name} />
          <DetailRow label="Catch Phrase" value={company.catchPhrase} />
          <DetailRow label="Business" value={company.bs} />
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F4F6F8',
    padding: 16,
    // SafeAreaView only pads on iOS, so leave room for the Android status bar.
    paddingTop: Platform.OS === 'android' ? (StatusBar.currentHeight || 24) + 8 : 16,
  },
  centered: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
    backgroundColor: '#F4F6F8',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginTop: 8,
    marginBottom: 12,
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    marginBottom: 16,
    color: '#111827',
  },
  searchInput: {
    backgroundColor: 'white',
    borderWidth: 1,
    borderColor: '#D1D5DB',
    borderRadius: 10,
    paddingHorizontal: 14,
    paddingVertical: 10,
    fontSize: 16,
    marginBottom: 12,
  },
  userItem: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'white',
    padding: 16,
    marginBottom: 10,
    borderRadius: 10,
    shadowColor: '#000',
    shadowOpacity: 0.05,
    shadowRadius: 4,
    shadowOffset: { width: 0, height: 2 },
    elevation: 2,
  },
  pressed: {
    opacity: 0.6,
  },
  userInfo: {
    flex: 1,
    marginLeft: 12,
  },
  userName: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#111827',
  },
  userEmail: {
    fontSize: 14,
    marginTop: 4,
    color: '#4B5563',
  },
  chevron: {
    fontSize: 28,
    color: '#9CA3AF',
    marginLeft: 8,
  },
  avatar: {
    backgroundColor: '#1D4ED8',
    justifyContent: 'center',
    alignItems: 'center',
  },
  avatarText: {
    color: 'white',
    fontWeight: 'bold',
  },
  message: {
    marginTop: 10,
    fontSize: 16,
    textAlign: 'center',
    color: '#4B5563',
  },
  errorText: {
    fontSize: 18,
    color: '#B00020',
    marginBottom: 16,
  },
  primaryButton: {
    backgroundColor: '#1D4ED8',
    paddingVertical: 8,
    paddingHorizontal: 14,
    borderRadius: 8,
  },
  primaryButtonText: {
    color: 'white',
    fontWeight: 'bold',
  },
  details: {
    paddingBottom: 30,
  },
  backButton: {
    alignSelf: 'flex-start',
    backgroundColor: '#1D4ED8',
    paddingVertical: 10,
    paddingHorizontal: 14,
    borderRadius: 8,
    marginTop: 8,
    marginBottom: 18,
  },
  backButtonText: {
    color: 'white',
    fontWeight: 'bold',
  },
  profileHeader: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 8,
  },
  profileName: {
    fontSize: 22,
    fontWeight: 'bold',
    color: '#111827',
  },
  profileUsername: {
    fontSize: 16,
    color: '#6B7280',
    marginTop: 2,
  },
  card: {
    backgroundColor: 'white',
    borderRadius: 10,
    padding: 16,
    paddingTop: 6,
    marginTop: 8,
  },
  sectionTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    marginTop: 20,
    marginBottom: 4,
    color: '#111827',
  },
  detailLabel: {
    fontSize: 13,
    fontWeight: 'bold',
    marginTop: 10,
    color: '#6B7280',
    textTransform: 'uppercase',
  },
  detailValue: {
    fontSize: 16,
    marginTop: 2,
    color: '#111827',
  },
  linkText: {
    color: '#1D4ED8',
    fontWeight: '600',
  },
  geoCard: {
    flexDirection: 'row',
    backgroundColor: '#EFF6FF',
  },
  geoItem: {
    flex: 1,
  },
  geoValue: {
    fontSize: 18,
    fontWeight: 'bold',
    marginTop: 2,
    color: '#1E3A8A',
  },
});
