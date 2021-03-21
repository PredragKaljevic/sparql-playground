//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.42000
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace FTN {
    using System;
    using FTN;
    
    
    /// Coordinate reference system.
    public class CoordinateSystem : IdentifiedObject {
        
        /// A Uniform Resource Name (URN) for the coordinate reference system (crs) used to define 'Location.PositionPoints'.
        ///An example would be the European Petroleum Survey Group (EPSG) code for a coordinate reference system, defined in URN under the Open Geospatial Consortium (OGC) namespace as: urn:ogc:def:crs:EPSG::XXXX, where XXXX is an EPSG code (a full list of codes can be found at the EPSG Registry web site http://www.epsg-registry.org/). To define the coordinate system as being WGS84 (latitude, longitude) using an EPSG OGC, this attribute would be urn:ogc:def:crs:EPSG::4236.
        ///A profile should limit this code to a set of allowed URNs agreed to by all sending and receiving parties.
        private string cim_crsUrn;
        
        private const bool isCrsUrnMandatory = false;
        
        private const string _crsUrnPrefix = "cim";
        
        public virtual string CrsUrn {
            get {
                return this.cim_crsUrn;
            }
            set {
                this.cim_crsUrn = value;
            }
        }
        
        public virtual bool CrsUrnHasValue {
            get {
                return this.cim_crsUrn != null;
            }
        }
        
        public static bool IsCrsUrnMandatory {
            get {
                return isCrsUrnMandatory;
            }
        }
        
        public static string CrsUrnPrefix {
            get {
                return _crsUrnPrefix;
            }
        }
    }
}