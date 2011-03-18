package dk.tbsalling.ais.messages;

import dk.tbsalling.ais.Decoder;
import dk.tbsalling.ais.exceptions.InvalidEncodedMessage;
import dk.tbsalling.ais.exceptions.UnsupportedMessageType;
import dk.tbsalling.ais.messages.types.AISMessageType;
import dk.tbsalling.ais.messages.types.IMO;
import dk.tbsalling.ais.messages.types.MMSI;
import dk.tbsalling.ais.messages.types.PositionFixingDevice;
import dk.tbsalling.ais.messages.types.ShipType;

/**
 * Message has a total of 424 bits, occupying two AIVDM sentences. In practice,
 * the information in these fields (especially ETA and destination) is not
 * reliable, as it has to be hand-updated by humans rather than gathered
 * automatically from sensors.
 * 
 * @author tbsalling
 */
@SuppressWarnings("serial")
public class ShipAndVoyageData extends DecodedAISMessage {
	
	private ShipAndVoyageData(Integer repeatIndicator,
			MMSI mmsi, IMO imo, String callsign, String shipName,
			ShipType shipType, Integer toBow, Integer toStern,
			Integer toStarboard, Integer toPort,
			PositionFixingDevice positionFixingDevice, String eta, Float draught,
			String destination, Boolean dataTerminalReady) {
		super(AISMessageType.ShipAndVoyageRelatedData, repeatIndicator, mmsi);
		this.imo = imo;
		this.callsign = callsign;
		this.shipName = shipName;
		this.shipType = shipType;
		this.toBow = toBow;
		this.toStern = toStern;
		this.toStarboard = toStarboard;
		this.toPort = toPort;
		this.positionFixingDevice = positionFixingDevice;
		this.eta = eta;
		this.draught = draught;
		this.destination = destination;
		this.dataTerminalReady = dataTerminalReady;
	}

	public final IMO getImo() {
		return imo;
	}

	public final String getCallsign() {
		return callsign;
	}

	public final String getShipName() {
		return shipName;
	}

	public final ShipType getShipType() {
		return shipType;
	}

	public final Integer getToBow() {
		return toBow;
	}

	public final Integer getToStern() {
		return toStern;
	}

	public final Integer getToStarboard() {
		return toStarboard;
	}

	public final Integer getToPort() {
		return toPort;
	}

	public final PositionFixingDevice getPositionFixingDevice() {
		return positionFixingDevice;
	}

	public final String getEta() {
		return eta;
	}

	public final Float getDraught() {
		return draught;
	}

	public final String getDestination() {
		return destination;
	}

	public final Boolean getDataTerminalReady() {
		return dataTerminalReady;
	}

	public static ShipAndVoyageData fromEncodedMessage(EncodedAISMessage encodedMessage) {
		if (! encodedMessage.isValid())
			throw new InvalidEncodedMessage(encodedMessage);
		if (! encodedMessage.getMessageType().equals(AISMessageType.ShipAndVoyageRelatedData))
			throw new UnsupportedMessageType(encodedMessage.getMessageType().getCode());
			
		Integer repeatIndicator = Decoder.convertToUnsignedInteger(encodedMessage.getBits(6, 8));
		MMSI sourceMmsi = MMSI.valueOf(Decoder.convertToUnsignedLong(encodedMessage.getBits(8, 38)));
		IMO imo = IMO.valueOf(Decoder.convertToUnsignedLong(encodedMessage.getBits(40, 70)));
		String callsign = Decoder.convertToString(encodedMessage.getBits(70, 112));
		String shipName = Decoder.convertToString(encodedMessage.getBits(112, 232));
		ShipType shipType = ShipType.fromInteger(Decoder.convertToUnsignedInteger(encodedMessage.getBits(232, 240)));
		Integer toBow = Decoder.convertToUnsignedInteger(encodedMessage.getBits(240, 249));
		Integer toStern = Decoder.convertToUnsignedInteger(encodedMessage.getBits(249, 258));
		Integer toPort = Decoder.convertToUnsignedInteger(encodedMessage.getBits(258, 264));
		Integer toStarboard = Decoder.convertToUnsignedInteger(encodedMessage.getBits(264, 270));
		PositionFixingDevice positionFixingDevice = PositionFixingDevice.fromInteger(Decoder.convertToUnsignedInteger(encodedMessage.getBits(270, 274)));
		String eta = Decoder.convertToTime(encodedMessage.getBits(274, 294));
		Float draught = (float) Decoder.convertToUnsignedInteger(encodedMessage.getBits(294, 302)) / (float) 10.0;
		String destination = Decoder.convertToString(encodedMessage.getBits(302, 422));
		Boolean dataTerminalReady = Decoder.convertToBoolean(encodedMessage.getBits(422, 423));
		
		return new ShipAndVoyageData(repeatIndicator, sourceMmsi, imo, callsign, shipName, shipType, toBow, toStern, toStarboard, toPort, positionFixingDevice, eta, draught, destination, dataTerminalReady);
	}
		
	private final IMO imo;
	private final String callsign;
	private final String shipName;
	private final ShipType shipType;
	private final Integer toBow;
	private final Integer toStern;
	private final Integer toStarboard;
	private final Integer toPort;
	private final PositionFixingDevice positionFixingDevice;
	private final String eta;
	private final Float draught;
	private final String destination;
	private final Boolean dataTerminalReady;
}
