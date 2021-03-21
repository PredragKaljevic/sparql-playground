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
    
    
    /// AnalogValue represents an analog MeasurementValue.
    public class AnalogValue : MeasurementValue {
        
        /// Measurement to which this value is connected.
        private Analog cim_Analog;
        
        private const bool isAnalogMandatory = true;
        
        private const string _AnalogPrefix = "cim";
        
        /// The value to supervise.
        private System.Single? cim_value;
        
        private const bool isValueMandatory = false;
        
        private const string _valuePrefix = "cim";
        
        public virtual Analog Analog {
            get {
                return this.cim_Analog;
            }
            set {
                this.cim_Analog = value;
            }
        }
        
        public virtual bool AnalogHasValue {
            get {
                return this.cim_Analog != null;
            }
        }
        
        public static bool IsAnalogMandatory {
            get {
                return isAnalogMandatory;
            }
        }
        
        public static string AnalogPrefix {
            get {
                return _AnalogPrefix;
            }
        }
        
        public virtual float Value {
            get {
                return this.cim_value.GetValueOrDefault();
            }
            set {
                this.cim_value = value;
            }
        }
        
        public virtual bool ValueHasValue {
            get {
                return this.cim_value != null;
            }
        }
        
        public static bool IsValueMandatory {
            get {
                return isValueMandatory;
            }
        }
        
        public static string ValuePrefix {
            get {
                return _valuePrefix;
            }
        }
    }
}