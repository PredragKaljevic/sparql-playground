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
    
    
    /// The parts of the AC power system that are designed to carry current or that are conductively connected through terminals.
    public class ConductingEquipment : Equipment {
        
        private System.Int32? cim_ratedVoltage;
        
        private const bool isRatedVoltageMandatory = false;
        
        private const string _ratedVoltagePrefix = "ftn";
        
        public virtual int RatedVoltage {
            get {
                return this.cim_ratedVoltage.GetValueOrDefault();
            }
            set {
                this.cim_ratedVoltage = value;
            }
        }
        
        public virtual bool RatedVoltageHasValue {
            get {
                return this.cim_ratedVoltage != null;
            }
        }
        
        public static bool IsRatedVoltageMandatory {
            get {
                return isRatedVoltageMandatory;
            }
        }
        
        public static string RatedVoltagePrefix {
            get {
                return _ratedVoltagePrefix;
            }
        }
    }
}
