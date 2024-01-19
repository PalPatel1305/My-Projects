import React, { useState, useEffect } from 'react';
import { View, Text, Button,ScrollView,Image,StyleSheet } from 'react-native';

const Event = ({ setScreen, url }) => {
  const [data, setData] = useState(null);

  useEffect(() => {
    // Fetch data when the component mounts
    fetchData();
  }, []);

  //fetch the data for that particular event
  const fetchData = async () => {
    try {
      const response = await fetch("https://app.ticketmaster.com"+url+"&apikey=GgRExJeAQWJ8LjmAUyCAbSTPTkBod4iI");
      if (!response.ok) {
        throw new Error('Network response was not ok.');
      }
      const result = await response.json();
      setData(result);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };


  return (
    data!=null?
    <ScrollView contentContainerStyle={styles.container}>
      <View style={styles.imageContainer}>
        {/* show the image */}
        {data.images.length > 0 && (
          <Image source={{ uri: data.images[0].url }} style={styles.image} />
        )}
      </View>

        {/* events details */}
      <View style={styles.detailsContainer}>
        <Text style={styles.eventName}>{data.name}</Text>
        <View style={styles.details}>
          <Text>
            <Text style={styles.label}>Start Date:</Text>{' '}
            {data.dates.start.localDate} - {data.dates.start.localTime}
          </Text>

          <Text>
            <Text style={styles.label}>Please Note:</Text> {data.pleaseNote}
          </Text>

          <Text>
            <Text style={styles.label}>Price Range:</Text>{' '}
            {data.priceRanges && data.priceRanges.length > 0? `${data.priceRanges[0].currency} ${data.priceRanges[0].min} - ${data.priceRanges[0].max}` : "Free"}
          </Text>

          {/* venue details */}
          <Text style={styles.venueTitle}>Venue Details:</Text>
          <Text>{data._embedded.venues[0].name}</Text>
          <Text>
            {data._embedded.venues[0].address.line1},{' '}
            {data._embedded.venues[0].city.name}, {data._embedded.venues[0].state.name},{' '}
            {data._embedded.venues[0].country.name}
          </Text>
          <View style={styles.buttonContainer}>
            <Button color="#666" title="All Events" onPress={() => setScreen('home')} style={styles.button} />
          </View>
        </View>
      </View>
    </ScrollView>:<><Text>Loading!!</Text></>
  );
};

//Page styles
const styles = StyleSheet.create({
  container: {
    flexGrow: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 20,
  },
  imageContainer: {
    alignItems: 'center',
    marginBottom: 20,
  },
  image: {
    width: 300,
    height: 200,
    borderRadius: 10,
  },
  detailsContainer: {
    width: '100%',
  },
  eventName: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 10,
    textAlign: 'center',
  },
  details: {
    backgroundColor: '#f5f5f5',
    padding: 10,
    borderRadius: 10,
    width: '100%',
  },
  label: {
    fontWeight: 'bold',
  },
  venueTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginTop: 10,
  },
  buttonContainer: {
    marginTop: 20,
    alignSelf: 'center',
    width: '50%',
    backgroundColor: '#ccc',
  },
  button: {
    backgroundColor: '#666', // Grey background color
    color: '#fff', // White text color
  },
});
export default Event;