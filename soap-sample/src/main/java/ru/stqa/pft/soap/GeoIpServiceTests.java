package ru.stqa.pft.soap;


import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {
  @Test
  public void testMyIp(){
  String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("77.88.55.242");
  assertEquals(ipLocation, "<GeoIP><Country>RU</Country><State>53</State></GeoIP>");
  }

  @Test
  public void testInvalidMyIp(){
    String ipLocation = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("17.88.55.200");
    assertEquals(ipLocation, "<GeoIP><Country>US</Country><State>CA</State></GeoIP>");
  }
}
