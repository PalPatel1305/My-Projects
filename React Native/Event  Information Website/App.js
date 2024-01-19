import { StatusBar } from 'expo-status-bar';
import { FlatList, StyleSheet, Button, Text, View, TextInput, TouchableOpacity } from 'react-native';
import Event from "./EventDetail.js";
import React, { useEffect, useState } from 'react';

function App() {
  const [events, setEvents] = useState([]);

  const [nextPage, setNextPage] = useState([]);
  const [lastPage, setLastPage] = useState([]);
  
  const [screen, setScreen] = useState('home');
  const [eventUrl, setEventUrl] = useState("");

  const [text, setChangeText] = useState('');
  const [searchResult, setSearchResult] = useState([]);
  const [showResults, setShowResults] = useState(false);

   /**
   * on Page load load the events for the music
   */
   useEffect(()=>{
    getEvents("music")
  },[])

  /**
   * 
   * @param {*} href take the href of event and set it to eventUrl
   */
  const handleEventPress = (href) => {
    setEventUrl(href);
    setScreen('event');
  }

  /**
   * 
   * @param {*} classificationName name of the category needed to fetch the events based on that
   */
  const getEvents = async (classificationName) => {
    try {
      if (classificationName != "") {
        // getting the today's date to get the events from the current date
        const today = new Date();
        const formattedDate = new Date(Date.UTC(today.getFullYear(), today.getMonth(), today.getDate(), 0, 0, 0));
        const formattedISOString = formattedDate.toISOString().replace(/\.\d+/, '');
        const response = await fetch("https://app.ticketmaster.com/discovery/v2/events.json?classificationName=" + classificationName + "&startDateTime=" + formattedISOString + "&sort=date,asc&countryCode=CA&apikey=GgRExJeAQWJ8LjmAUyCAbSTPTkBod4iI");
        const json = await response.json();

        setEvents(json._embedded.events);
        setLastPage(json._links.prev ? json._links.prev.href : "");
        setNextPage(json._links.next ? json._links.next.href : "");
      }

    } catch (error) { console.error(error); }
  }

 

  /**
   * handle the page changes
   * @param {*} url the url of the previous or next page
   */
  const handlePagination = async (url) => {
    if (url != "") {

      try {
        //get the data of respective page
        const response = await fetch("https://app.ticketmaster.com" + url + "&apikey=GgRExJeAQWJ8LjmAUyCAbSTPTkBod4iI");
        const json = await response.json();
        setEvents(json._embedded.events);
        setLastPage(json._links.prev ? json._links.prev.href : "");
        setNextPage(json._links.next ? json._links.next.href : "");
      } catch (error) { console.error(error); }
    }

  }

  /**
   * 
   * @param {*} input user input to what classification they need to get the events
   */
  const getSearchClassification = async (input) => {
    if (input != "") {
      //if the input is all then adjusting the parameter to get the data
      if (input == "all") {
        input = "";
      }
      let url = "/discovery/v2/classifications?keyword=" + input;
      const response = await fetch("https://app.ticketmaster.com" + url + "&apikey=GgRExJeAQWJ8LjmAUyCAbSTPTkBod4iI");
      const json = await response.json();
      
      if (json.page.totalElements != 0) {
        const namesArray = json._embedded.classifications
          .map((classification) => classification.segment ? classification.segment.name : classification.type.name);
        setSearchResult(namesArray);
        setShowResults(true);
      } else {
        setSearchResult([])
        setShowResults(true);
      }

    }
  }
  /**
   * 
   * @param {*} item taking the item and renders it
   * @returns the table row of the events list
   */
  const renderItem = ({ item }) => (
    <View style={styles.tableRow}>
      <View style={styles.tableCell}>
        <Text style={styles.cellText}>{item.name}</Text>
      </View>
      <View style={styles.tableCell}>
        <Text style={styles.cellText}>{item.dates.start.localDate}</Text>
      </View>
      <TouchableOpacity
        style={styles.tableCell}
        onPress={() => handleEventPress(item._links.self.href)}
      >
        <Text style={[styles.cellText, styles.detailsLink]}>Details</Text>
      </TouchableOpacity>
      <View style={styles.tableCell}>
        <Text>{item._embedded.venues[0].name}</Text>
      </View>
    </View>
  );

  return (

    <View style={styles.container}>
      {screen === 'home' && (
        <View style={styles.homeContainer}>
          <View style={styles.header}>
            <Text style={styles.headerTitle}>EventVue</Text>
          </View>
          <Text style={styles.heading}>Classifications Filter Fetch</Text>
          <TextInput //input for categories
            style={styles.input}
            onChangeText={setChangeText}
            value={text}
            placeholder="Type here classification categories like music or sports or all"
          />
          
          <Button //button for fetching the classification
            onPress={() => getSearchClassification(text)}
            title="Fetch Classifications"
            color="#666" 
          />
          {showResults && (
            <Text style={styles.availableFilters}>Available filters:</Text>
          )}
          
          {showResults ? (//show the available categories 
            searchResult.length > 0 ? (
              <View style={styles.filterButtonsContainer}>
                {searchResult.map((name, index) => (
                  <View style={styles.filterButton} key={index}>
                    <Button
                      title={name}
                      onPress={() => getEvents(name)}
                      color="#666"
                    />
                  </View>
                ))}
              </View>
            ) : (
              <Text style={styles.noSearchFound}>No Search found</Text>
            )
          ) : (
            <></>
          )}
          
          <View style={styles.tableContainer}> 
            <View style={styles.tableHeader}>
              <Text style={styles.headerText}>Event</Text>
              <Text style={styles.headerText}>Start Date</Text>
              <Text style={styles.headerText}>Event Details</Text>
              <Text style={styles.headerText}>Venue Details</Text>
            </View>
            
            <FlatList //events data
              data={events}
              renderItem={renderItem}
              keyExtractor={(item) => item.id.toString()}
              style={styles.flatList}
            />
            {/* handle the pagination buttons */}
            <View style={styles.paginationButtons}>
              <Button color="#666" onPress={() => handlePagination(lastPage)} title="Last Page" />
              <Button color="#666" onPress={() => handlePagination(nextPage)} title="Next Page" />
            </View>
          </View>
        </View>
      )}
      {/* Show the events details page  */}
      {screen === 'event' && <Event setScreen={setScreen} url={eventUrl} />}
      <StatusBar style="auto" />
    </View>

  );
};

const styles = StyleSheet.create({
  //container styles
  container: {
    flex: 1,
    padding: 20,
    backgroundColor: '#f0f0f0', 
  },
  
  //home container styles
  homeContainer: {
    flex: 1,
  },

  //heading styles
  heading: {
    fontSize: 20,
    marginBottom: 10,
    color: '#333', 
  },

  //input box styles
  input: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    marginBottom: 10,
    paddingHorizontal: 10,
  },

  //availableFilters styles
  availableFilters: {
    fontSize: 16,
    fontWeight: 'bold',
    marginTop: 10,
    color: '#333', 
  },

  // filterButtonsContainer styles
  filterButtonsContainer: {
    flexDirection: 'row',
    flexWrap: 'wrap',
    justifyContent: 'center',
    alignItems: 'center',
  },
  //  filterButton styles
  filterButton: {
    margin: 10,
    backgroundColor: '#666', 
    borderRadius: 5,
    overflow: 'hidden',
  },
  //  noSearchFound styles
  noSearchFound: {
    marginTop: 10,
    color: '#333',
    fontWeight:'bold',
    alignItems: 'center',
  },
  //  tableContainer styles
  tableContainer: {
    flex: 1,
    marginTop: 20,
  },
  // tableHeader styles
  tableHeader: {
    flexDirection: 'row',
    borderBottomWidth: 1,
    paddingVertical: 10,
    justifyContent: 'space-around',
    backgroundColor: 'lightgrey',
    marginBottom: 5,
  },
  // headerText styles
  headerText: {
    fontWeight: 'bold',
    flex: 1,
    textAlign: 'center',
    color: '#333',
  },
  // flatList styles
  flatList: {
    flex: 1,
  },
  // paginationButtons styles
  paginationButtons: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginTop: 10,
  },
  // tableRow styles
  tableRow: {
    flexDirection: 'row',
    borderBottomWidth: 1,
    borderBottomColor: '#ccc',
    paddingVertical: 10,
    alignItems: 'center', 
    backgroundColor: 'lightgrey', 
  },
  // tableCell styles
  tableCell: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 10,
  },
  // cellText styles
  cellText: {
    color: '#333',
    fontSize: 16,
    fontWeight: 'bold',
    textAlign: 'center', 
  },
  // detailsLink styles
  detailsLink: {
    textDecorationLine: 'underline',
    color: 'black',
    fontStyle: 'italic',
    fontWeight: 'bold',
  },
  //header styles
  header: {
    backgroundColor: '#f0f0f0',
    width: '100%',
    paddingVertical: 20,
    paddingHorizontal: 15,
    alignItems: 'center',
  },
  // headerTitle styles
  headerTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#333',
  },
});

export default App;