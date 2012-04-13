package org.cloudsherpa.ui.server;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.cloudsherpa.ui.client.UIService;
import org.openstack.model.compute.Flavor;
import org.openstack.model.compute.FloatingIp;
import org.openstack.model.compute.KeyPair;
import org.openstack.model.compute.SecurityGroup;
import org.openstack.model.compute.SecurityGroupForCreate;
import org.openstack.model.compute.SecurityGroupRule;
import org.openstack.model.compute.SecurityGroupRuleForCreate;
import org.openstack.model.compute.Server;
import org.openstack.model.compute.ServerAction;
import org.openstack.model.compute.ServerForCreate;
import org.openstack.model.compute.Snapshot;
import org.openstack.model.compute.SnapshotForCreate;
import org.openstack.model.compute.Volume;
import org.openstack.model.compute.nova.NovaAddressList;
import org.openstack.model.compute.nova.NovaAddressList.Network;
import org.openstack.model.compute.nova.NovaAddressList.Network.Ip;
import org.openstack.model.compute.nova.NovaFault;
import org.openstack.model.compute.nova.NovaFlavor;
import org.openstack.model.compute.nova.NovaMetadata;
import org.openstack.model.compute.nova.NovaServer;
import org.openstack.model.compute.nova.floatingip.NovaFloatingIp;
import org.openstack.model.compute.nova.keypair.NovaKeyPair;
import org.openstack.model.compute.nova.securitygroup.NovaSecurityGroup;
import org.openstack.model.compute.nova.securitygroup.NovaSecurityGroupRule;
import org.openstack.model.compute.nova.snapshot.NovaSnapshot;
import org.openstack.model.compute.nova.volume.NovaVolume;
import org.openstack.model.compute.nova.volume.VolumeForCreate;
import org.openstack.model.identity.Endpoint;
import org.openstack.model.identity.Role;
import org.openstack.model.identity.Service;
import org.openstack.model.identity.Tenant;
import org.openstack.model.identity.User;
import org.openstack.model.identity.UserForCreate;
import org.openstack.model.identity.keystone.KeystoneEndpoint;
import org.openstack.model.identity.keystone.KeystoneRole;
import org.openstack.model.identity.keystone.KeystoneService;
import org.openstack.model.identity.keystone.KeystoneTenant;
import org.openstack.model.identity.keystone.KeystoneUser;
import org.openstack.model.images.Image;
import org.openstack.model.images.glance.GlanceImage;

import com.google.common.collect.Maps;
import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UIMockServiceImpl extends RemoteServiceServlet implements UIService {
	
	private static Map<String, Server> servers = Maps.newConcurrentMap();
	private static Map<String, Flavor> flavors = Maps.newConcurrentMap();
	private static Map<String, Image> images = Maps.newConcurrentMap();
	private static Map<Integer, FloatingIp> floatingIps = Maps.newConcurrentMap();
	private static Map<Integer, Volume> volumes = Maps.newConcurrentMap();
	private static Map<Integer, Snapshot> snapshots = Maps.newConcurrentMap();
	private static Map<String, KeyPair> keyPairs = Maps.newConcurrentMap();
	private static Map<Integer, SecurityGroup> securityGroups = Maps.newConcurrentMap();
	
	private static Map<String, Tenant> tenants = Maps.newConcurrentMap();
	private static Map<String, User> users = Maps.newConcurrentMap();
	private static Map<String, Role> roles = Maps.newConcurrentMap();
	private static Map<String, Service> services = Maps.newConcurrentMap();
	private static Map<String, Endpoint> endpoints = Maps.newConcurrentMap();
	
	static {
		NovaMetadata m = new NovaMetadata();
		for(int i = 0; i < 5; i++) {
			m.getItems().add(new NovaMetadata.Item("k."+i, "v."+i));
		}
		
		NovaAddressList addresses = new NovaAddressList();
		for(int i = 0; i < 2; i++) {
			Network n = new Network();
			n.setId("network."+i);
			for(int j = 0; j < 3; j++) {
				n.getIps().add(new Ip("4","192.168.1.1"));
			}
			addresses.getNetworks().add(n);
		}
		
		List<SecurityGroupRule> rules = new ArrayList<SecurityGroupRule>();
		for(int i = 0; i < 2; i++) {
			NovaSecurityGroupRule rule = new NovaSecurityGroupRule();
			rule.setIpProtocol("TCP");
			rule.setFromPort(22);
			rule.setToPort(32);
			rule.getIpRange().setCidr("0.0.0.0/8");
			rules.add(rule);
		}
		
		for(int i = 0; i < 3; i++) {
			GlanceImage image = new GlanceImage(String.valueOf(i),"image."+i);
			image.setChecksum("12345678dsdhfjg");
			image.setOwner("owner34434");
			image.setContainerFormat("OVF");
			image.setDiskFormat("JCOW2");
			image.setMinDisk(1);
			image.setMinRam(2);
			image.setSize(98766L);
			image.setDeleted(true);
			image.setDeletedAt(new Date());
			image.setUpdatedAt("12/04/1890");
			image.setCreatedAt("12/04/1890");
			image.setProtected(true);
			image.setPublic(true);
			image.setName("testing image");
			image.setStatus("ACTIVE");
			image.setUri("http://122121212121");
			images.put(String.valueOf(i), image);
			NovaFlavor flavor = new NovaFlavor(String.valueOf(i),"flavor."+i);
			flavors.put(String.valueOf(i), flavor);
			NovaServer server = new NovaServer(String.valueOf(i),"server."+i);
			server.setConfigDrive("config_drive");
			server.setCreated(new Date());
			server.setUpdated(new Date());
			server.setFault(new NovaFault(i, "12/04/2012", "test fault", "test fault desc"));
			//server.setImage(image);
			server.setFlavor(flavor);
			server.setHostId("1234");
			server.setKeyName("Keyname");
			server.setProgress("100");
			server.setStatus("ACTIVE");
			server.setTenantId("tenant");
			server.setUserId("userId");
			server.setMetadata(m);
			server.setAddresses(addresses);
			servers.put(String.valueOf(i), server);
			floatingIps.put(i, new NovaFloatingIp(i, String.valueOf(i), String.valueOf(i), "nova", String.valueOf(i)));
			volumes.put(i, new NovaVolume(i, "volume."+i));
			snapshots.put(i, new NovaSnapshot());
			keyPairs.put("key."+i, new NovaKeyPair("key."+i));
			NovaSecurityGroup securityGroup = new NovaSecurityGroup(i,"security group."+i,"security group."+i+" desc");
			securityGroup.setRules(rules);
			securityGroups.put(i, securityGroup);
			
			
			tenants.put(String.valueOf(i), new KeystoneTenant(String.valueOf(i),"tenant."+i));
			users.put(String.valueOf(i), new KeystoneUser(String.valueOf(i),"user."+i));
			roles.put(String.valueOf(i), new KeystoneRole(String.valueOf(i),"role."+i));
			services.put(String.valueOf(i), new KeystoneService(String.valueOf(i), "service."+i, "compute", "desc"));
			endpoints.put(String.valueOf(i), new KeystoneEndpoint(String.valueOf(i), "nova", String.valueOf(i)));
			
			
		}
	}
	

	@Override
	public List<Server> listServers(int start, int max) {
		return Lists.newArrayList(servers.values());
	}

	@Override
	public Server createServer(ServerForCreate serverForCreate) {
		return new NovaServer();
	}

	@Override
	public void deleteServer(String id) {
		servers.remove(id);
	}

	@Override
	public Serializable executeServerAction(Collection<String> id, ServerAction action) {
		return new NovaServer();
	}

	@Override
	public List<Flavor> listFlavors(int start, int max) {
		return Lists.newArrayList(flavors.values());
	}

	@Override
	public Flavor createFlavor(Flavor flavor) {
		return flavor;
	}

	@Override
	public void deleteFlavor(String id) {
		flavors.remove(id);
	}

	@Override
	public List<Image> listImages(int starts, int max) {
		return Lists.newArrayList(images.values());
	}

	@Override
	public Image createImage(Image image) {
		return new GlanceImage();
	}

	@Override
	public void deleteImage(String id) {
		images.remove(id);
	}

	@Override
	public List<FloatingIp> listFloatingIps() {
		return Lists.newArrayList(floatingIps.values());
	}

	@Override
	public FloatingIp createFloatingIp() {
		return new NovaFloatingIp();
	}

	@Override
	public void deleteFloatingIp(Integer id) {
		floatingIps.remove(id);
	}

	@Override
	public FloatingIp associateFloatingIp(String ip, String serverId) {
		return new NovaFloatingIp();
	}

	@Override
	public FloatingIp disassociateFloatingIp(String ip) {
		return new NovaFloatingIp();
	}

	@Override
	public List<Volume> listVolumes() {
		return Lists.newArrayList(volumes.values());
	}

	@Override
	public Volume createVolume(VolumeForCreate volumeForCreate) {
		return new NovaVolume();
	}

	@Override
	public void deleteVolume(Integer id) {
		volumes.remove(id);
	}

	@Override
	public Volume attachVolume(Integer id, String serverId) {
		return new NovaVolume();
	}

	@Override
	public Volume detachVolume(Integer id) {
		return new NovaVolume();
	}

	@Override
	public List<Snapshot> listSnapshots() {
		return Lists.newArrayList(snapshots.values());
	}

	@Override
	public Snapshot createSnapshots(SnapshotForCreate snapshotForCreate) {
		return new NovaSnapshot();
	}

	@Override
	public void deleteSnapshot(Integer id) {
		snapshots.remove(id);
	}

	@Override
	public List<KeyPair> listKeyPairs() {
		return Lists.newArrayList(keyPairs.values());
	}

	@Override
	public KeyPair createKeyPair(String name) {
		return new NovaKeyPair();
	}

	@Override
	public void deleteKeyPair(String name) {
		keyPairs.remove(name);
	}

	@Override
	public List<SecurityGroup> listSecurityGroups() {
		return Lists.newArrayList(securityGroups.values());
	}

	@Override
	public SecurityGroup createSecurityGroup(SecurityGroupForCreate securityGroup) {
		return new NovaSecurityGroup();
	}

	@Override
	public void deleteSecurityGroup(Integer id) {
		securityGroups.remove(id);
	}

	@Override
	public SecurityGroupRule createSecurityGroupRule(SecurityGroupRuleForCreate rule) {
		return new NovaSecurityGroupRule();
	}

	@Override
	public void deleteSecurityGroupRule(Integer id) {
		
	}

	@Override
	public List<Tenant> listTenants(int start, int max) {
		return Lists.newArrayList(tenants.values());
	}

	@Override
	public Tenant createTenant(Tenant tenant) {
		tenant.setId(String.valueOf(System.currentTimeMillis()));
		tenants.put(tenant.getId(), tenant);
		return tenant;
	}

	@Override
	public void deleteTenant(String id) {
		tenants.remove(id);
	}
	
	@Override
	public void deleteTenants(String[] ids) {
		for(String id : ids) {
			deleteTenant(id);
		}
	}

	@Override
	public List<User> listUsers() {
		return Lists.newArrayList(users.values());
	}

	@Override
	public User createUser(UserForCreate userForCreate) {
		User user = new KeystoneUser(String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()));
		users.put(user.getId(), user);
		return user;
	}

	@Override
	public void deleteUser(String id) {
		users.remove(id);
	}

	@Override
	public List<Role> listRoles() {
		return Lists.newArrayList(roles.values());
	}

	@Override
	public Role createRole(Role role) {
		role.setId(String.valueOf(System.currentTimeMillis()));
		roles.put(role.getId(), role);
		return role;
	}

	@Override
	public void deleteRole(String id) {
		roles.remove(id);
	}

	@Override
	public List<Service> listServices() {
		return Lists.newArrayList(services.values());
	}

	@Override
	public Service createService(Service service) {
		service.setId(String.valueOf(System.currentTimeMillis()));
		services.put(service.getId(), service);
		return service;
	}

	@Override
	public void deleteService(String id) {
		services.remove(id);
	}

	@Override
	public List<Endpoint> listEndpoints() {
		return Lists.newArrayList(endpoints.values());
	}

	@Override
	public Endpoint createEndpoint(Endpoint endpoint) {
		endpoint.setId(String.valueOf(System.currentTimeMillis()));
		endpoints.put(endpoint.getId(), endpoint);
		return endpoint;
	}

	@Override
	public void deleteEndpoint(String id) {
		endpoints.remove(id);
	}

}
