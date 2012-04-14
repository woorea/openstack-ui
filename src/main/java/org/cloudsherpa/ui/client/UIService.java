package org.cloudsherpa.ui.client;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
import org.openstack.model.compute.nova.volume.VolumeForCreate;
import org.openstack.model.identity.Endpoint;
import org.openstack.model.identity.Role;
import org.openstack.model.identity.Service;
import org.openstack.model.identity.Tenant;
import org.openstack.model.identity.User;
import org.openstack.model.identity.UserForCreate;
import org.openstack.model.images.Image;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("../ui/service")
public interface UIService extends RemoteService {
	
	public List<Server> listServers(int start, int max);
	
	public Server createServer(ServerForCreate serverForCreate);
	
	public void deleteServer(String id);
	
	public void deleteServers(String[] ids);
	
	public Serializable executeServerAction(Collection<String> id, ServerAction action);
	
	public List<Flavor> listFlavors(int start, int max);
	
	public Flavor createFlavor(Flavor flavor);
	
	public void deleteFlavor(String id);
	
	public void deleteFlavors(String[] ids);
	
	public List<Image> listImages(int starts, int max);
	
	public Image createImage(Image image);
	
	public void deleteImage(String id);
	
	public void deleteImages(String[] id);
	
	public List<FloatingIp> listFloatingIps();
	
	public FloatingIp createFloatingIp();
	
	public void deleteFloatingIp(Integer id);
	
	public void deleteFloatingIps(Integer[] ids);
	
	public FloatingIp associateFloatingIp(String ip, String serverId);
	
	public FloatingIp disassociateFloatingIp(String ip);
	
	public List<Volume> listVolumes();
	
	public Volume createVolume(VolumeForCreate volumeForCreate);
	
	public void deleteVolume(Integer id);
	
	public void deleteVolumes(Integer[] id);
	
	public Volume attachVolume(Integer id, String serverId);
	
	public Volume detachVolume(Integer id);
	
	public List<Snapshot> listSnapshots();
	
	public Snapshot createSnapshots(SnapshotForCreate snapshotForCreate);
	
	public void deleteSnapshot(Integer ids);
	
	public void deleteSnapshots(Integer[] ids);
	
	public List<KeyPair> listKeyPairs();
	
	public KeyPair createKeyPair(String name);
	
	public void deleteKeyPair(String name);
	
	public void deleteKeyPairs(String[] names);
	
	public List<SecurityGroup> listSecurityGroups();
	
	public SecurityGroup createSecurityGroup(SecurityGroupForCreate securityGroup);
	
	public void deleteSecurityGroup(Integer id);
	
	public void deleteSecurityGroups(Integer[] ids);
	
	public SecurityGroupRule createSecurityGroupRule(SecurityGroupRuleForCreate rule);
	
	public void deleteSecurityGroupRule(Integer id);
	
	public void deleteSecurityGroupRules(Integer[] ids);
	
	//
	
	public List<Tenant> listTenants(int start, int max);
	
	public Tenant createTenant(Tenant tenant);
	
	public void deleteTenant(String id);
	
	public void deleteTenants(String[] ids);
	
	public List<User> listUsers();
	
	public User createUser(UserForCreate userForCreate);
	
	public void deleteUser(String id);
	
	public void deleteUsers(String[] ids);
	
	public List<Role> listRoles();
	
	public Role createRole(Role role);
	
	public void deleteRole(String id);
	
	public void deleteRoles(String[] ids);
	
	public List<Service> listServices();
	
	public Service createService(Service service);
	
	public void deleteService(String id);
	
	public void deleteServices(String[] ids);
	
	public List<Endpoint> listEndpoints();
	
	public Endpoint createEndpoint(Endpoint endpoint);
	
	public void deleteEndpoint(String id);
	
	public void deleteEndpoints(String[] ids);
}
